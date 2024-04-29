package com.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import com.bumptech.glide.Glide;
import com.travel.Activity.DetailFlightActivity;
import com.travel.Activity.DetailHotelActivity;
import com.travel.Activity.DetailRestaurantActivity;
import com.travel.Activity.DetailTourActivity;
import com.travel.Activity.MyRatingActivity;
import com.travel.Activity.RatingHistoryActivity;
import com.travel.Model.BookFlightModel;
import com.travel.Model.HotelBookingReviewModel;
import com.travel.Model.RestaurantBookingReviewModel;
import com.travel.Model.ReviewType;
import com.travel.Model.TourBookingModel;
import com.travel.Model.TourBookingReviewModel;
import com.travel.R;

public class HistoryCardAdapter<T> extends RecyclerView.Adapter<HistoryCardAdapter.HistoryCardViewHolder> {
    private List<T> listItem;
    private Context context;

    public HistoryCardAdapter(List<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryCardAdapter.HistoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_card, parent, false);
        return new HistoryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryCardAdapter.HistoryCardViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof TourBookingReviewModel) {
            bindTourBookingModel(holder, (TourBookingReviewModel) item);
        } else if (item instanceof RestaurantBookingReviewModel) {
            bindRestaurantBookingModel(holder, (RestaurantBookingReviewModel) item);
        } else if (item instanceof HotelBookingReviewModel) {
            bindHotelBookingModel(holder, (HotelBookingReviewModel) item);
        } else if (item instanceof BookFlightModel) {
            bindFlightBookingModel(holder, (BookFlightModel) item);
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class HistoryCardViewHolder extends RecyclerView.ViewHolder {
        TextView tourName, tourDescription, createdAt, status;
        View tourImage;
        Button ratingButton;

        public HistoryCardViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            tourName = itemView.findViewById(R.id.tour_name);
            tourImage = itemView.findViewById(R.id.tour_img);
            tourDescription = itemView.findViewById(R.id.tour_description);
            createdAt = itemView.findViewById(R.id.date);
            ratingButton = itemView.findViewById(R.id.rating_button);
        }
    }

    private void bindTourBookingModel(HistoryCardViewHolder holder, TourBookingReviewModel tourBookingModel) {
        View view = holder.itemView;
        holder.tourName.setText(tourBookingModel.getTour().getName());
        Glide.with(context).load(tourBookingModel.getTour().getImage()).into(((ImageView) view.findViewById(R.id.image)));
        // shortcut description
        String description = tourBookingModel.getTour().getDescription();
        if (description.length() > 150) {
            description = description.substring(0, 150) + "...";
        }
        holder.tourDescription.setText(description);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(tourBookingModel.getCreatedAt());
        holder.createdAt.setText("Đã đặt ngày " + date);

        if (tourBookingModel.getReview().getReviewId() != 0) {
            holder.ratingButton.setVisibility(View.GONE);
            holder.status.setVisibility(View.VISIBLE);
            holder.ratingButton.setEnabled(false);
        } else {
            holder.ratingButton.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.GONE);
            holder.ratingButton.setEnabled(true);

            holder.ratingButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, MyRatingActivity.class);
                intent.putExtra("bookingId", tourBookingModel.getBookingId());
                intent.putExtra("itemId", tourBookingModel.getTour().getTourId());
                intent.putExtra("type", ReviewType.TOUR.toString());
                context.startActivity(intent);
            });
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailTourActivity.class);
            intent.putExtra("tourId", tourBookingModel.getTour().getTourId());
            context.startActivity(intent);
        });
    }

    private void bindRestaurantBookingModel(HistoryCardViewHolder holder, RestaurantBookingReviewModel restaurantBookingModel) {
        View view = holder.itemView;
        holder.tourName.setText(restaurantBookingModel.getRestaurant().getName());
        Glide.with(context).load(restaurantBookingModel.getRestaurant().getImage()).into(((ImageView) view.findViewById(R.id.image)));
        // shortcut description
        String description = restaurantBookingModel.getRestaurant().getDescription();
        if (description.length() > 150) {
            description = description.substring(0, 150) + "...";
        }
        holder.tourDescription.setText(description);

        if (restaurantBookingModel.getReview().getReviewId() != 0) {
            holder.ratingButton.setVisibility(View.GONE);
            holder.status.setVisibility(View.VISIBLE);
            holder.ratingButton.setEnabled(false);
        } else {
            holder.ratingButton.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.GONE);
            holder.ratingButton.setEnabled(true);

            holder.ratingButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, MyRatingActivity.class);
                intent.putExtra("bookingId", restaurantBookingModel.getBookingId());
                intent.putExtra("itemId", restaurantBookingModel.getRestaurant().getRestaurantId());
                intent.putExtra("type", ReviewType.RESTAURANT.toString());
                context.startActivity(intent);
            });
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailRestaurantActivity.class);
            intent.putExtra("restaurantId", restaurantBookingModel.getRestaurant().getRestaurantId());
            context.startActivity(intent);
        });
    }

    private void bindHotelBookingModel(HistoryCardViewHolder holder, HotelBookingReviewModel hotelBookingModel) {
        View view = holder.itemView;
        holder.tourName.setText(hotelBookingModel.getHotel().getName());
        Glide.with(context).load(hotelBookingModel.getHotel().getImage()).into(((ImageView) view.findViewById(R.id.image)));
        // shortcut description
        String description = hotelBookingModel.getHotel().getDescription();
        if (description.length() > 150) {
            description = description.substring(0, 150) + "...";
        }
        holder.tourDescription.setText(description);
        if (hotelBookingModel.getReview().getReviewId() != 0) {
            holder.ratingButton.setVisibility(View.GONE);
            holder.status.setVisibility(View.VISIBLE);
            holder.ratingButton.setEnabled(false);
        } else {
            holder.ratingButton.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.GONE);
            holder.ratingButton.setEnabled(true);

            holder.ratingButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, MyRatingActivity.class);
                intent.putExtra("bookingId", hotelBookingModel.getBookingId());
                intent.putExtra("itemId", hotelBookingModel.getHotel().getHotelId());
                intent.putExtra("type", ReviewType.HOTEL.toString());
                context.startActivity(intent);
            });
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailHotelActivity.class);
            intent.putExtra("hotelId", hotelBookingModel.getHotel().getHotelId());
            context.startActivity(intent);
        });
    }

    private void bindFlightBookingModel(HistoryCardViewHolder holder, BookFlightModel flightBooking) {
        View view = holder.itemView;
        holder.tourName.setText(flightBooking.getFlight().getDepartureAirportCode() + " - " + flightBooking.getFlight().getArrivalAirportCode());
        Glide.with(context).load(R.drawable.flight).into(((ImageView) view.findViewById(R.id.image)));
        // shortcut description
        String description = flightBooking.getFlight().getDescription();
        if (description.length() > 150) {
            description = description.substring(0, 150) + "...";
        }
        holder.tourDescription.setText(description);

        holder.ratingButton.setVisibility(View.GONE);
        holder.status.setVisibility(View.GONE);
        holder.ratingButton.setEnabled(false);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailFlightActivity.class);
            intent.putExtra("flightId", flightBooking.getFlight().getFlightId());
            context.startActivity(intent);
        });

    }
}
