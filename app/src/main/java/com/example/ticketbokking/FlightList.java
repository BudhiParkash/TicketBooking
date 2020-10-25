package com.example.ticketbokking;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbokking.adapter.TicketAdapter;
import com.example.ticketbokking.network.ApiClient;
import com.example.ticketbokking.network.ApiService;
import com.example.ticketbokking.model.Price;
import com.example.ticketbokking.model.Ticket;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FlightList extends AppCompatActivity implements TicketAdapter.TicketsAdapterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    String from;
    String to;

    ProgressBar mListProgressbar;

    private CompositeDisposable disposable = new CompositeDisposable();


    private ApiService apiService;
    private TicketAdapter mAdapter;
    private ArrayList<Ticket> ticketsList = new ArrayList<>();


    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private TextView mTxtfrom;
    private TextView mTxtto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);
        initView();

        mListProgressbar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        from = intent.getStringExtra("fcity");
        to = intent.getStringExtra("tcity");

        mTxtfrom.setText(from);
        mTxtto.setText(to);

        apiService = ApiClient.getClient().create(ApiService.class);

        mAdapter = new TicketAdapter(this, ticketsList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        ConnectableObservable<List<Ticket>> ticketsObservable = getTickets(from, to).replay();

        /**
         * Fetching all tickets first
         * Observable emits List<Ticket> at once
         * All the items will be added to RecyclerView
         * */
        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<Ticket>>() {

                            @Override
                            public void onNext(List<Ticket> tickets) {
                                // Refreshing list
                                ticketsList.clear();
                                ticketsList.addAll(tickets);
                                mAdapter.notifyDataSetChanged();
                                mListProgressbar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                showError(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));

        /**
         * Fetching individual ticket price
         * First FlatMap converts single List<Ticket> to multiple emissions
         * Second FlatMap makes HTTP call on each Ticket emission
         * */
        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        /**
                         * Converting List<Ticket> emission to single Ticket emissions
                         * */
                        .flatMap(new Function<List<Ticket>, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(List<Ticket> tickets) throws Exception {
                                return Observable.fromIterable(tickets);
                            }
                        })
                        /**
                         * Fetching price on each Ticket emission
                         * */
                        .flatMap(new Function<Ticket, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(Ticket ticket) throws Exception {
                                return getPriceObservable(ticket);
                            }
                        })
                        .subscribeWith(new DisposableObserver<Ticket>() {

                            @Override
                            public void onNext(Ticket ticket) {
                                int position = ticketsList.indexOf(ticket);

                                if (position == -1) {
                                    // TODO - take action
                                    // Ticket not found in the list
                                    // This shouldn't happen
                                    return;
                                }

                                ticketsList.set(position, ticket);
                                mAdapter.notifyItemChanged(position);
                                mListProgressbar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                showError(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));

        // Calling connect to start emission
        ticketsObservable.connect();
        initView();
    }

    /**
     * Making Retrofit call to fetch all tickets
     */
    private Observable<List<Ticket>> getTickets(String from, String to) {
        return apiService.searchTickets(from, to)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Making Retrofit call to get single ticket price
     * get price HTTP call returns Price object, but
     * map() operator is used to change the return type to Ticket
     */
    private Observable<Ticket> getPriceObservable(final Ticket ticket) {
        return apiService
                .getPrice(ticket.getFlightNumber(), ticket.getFrom(), ticket.getTo())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Price, Ticket>() {
                    @Override
                    public Ticket apply(Price price) throws Exception {
                        ticket.setPrice(price);
                        return ticket;
                    }
                });
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTxtfrom = findViewById(R.id.txtfrom);
        mTxtto = findViewById(R.id.txtto);
        mListProgressbar = findViewById(R.id.listprogressbar);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onTicketSelected(Ticket contact) {

    }

    /**
     * Snackbar shows observer error
     */
    private void showError(Throwable e) {
        Log.e(TAG, "showError: " + e.getMessage());

        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();

    }
}