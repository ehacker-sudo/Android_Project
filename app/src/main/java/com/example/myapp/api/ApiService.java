package com.example.myapp.api;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myapp.MovieActivity;
import com.example.myapp.R;
import com.example.myapp.adapter.AlbumAdapter;
import com.example.myapp.adapter.CastAdapter;
import com.example.myapp.adapter.CollectionAdapter;
import com.example.myapp.adapter.CrewAdapter;
import com.example.myapp.adapter.GenresAdapter;
import com.example.myapp.adapter.MovieAdapter;
import com.example.myapp.adapter.MoviePagnateAdapter;
import com.example.myapp.adapter.TvExtraInfoAdapter;
import com.example.myapp.adapter.TvPagnateAdapter;
import com.example.myapp.adapter.TvSerieAdapter;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.databinding.ActivityPaginateBinding;
import com.example.myapp.film_interface.ExtraInfoListener;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.film_interface.PaginationScrollListener;
import com.example.myapp.filter.PaginateActivity;
import com.example.myapp.model.film.ExtraInfo;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.MovieInfo;
import com.example.myapp.model.film.TvSerie;
import com.example.myapp.model.film.TvSerieInfo;
import com.example.myapp.model.resource.CreditsResource;
import com.example.myapp.model.resource.FilmResource;
import com.example.myapp.model.resource.ImageType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService {
    public static Callback<FilmResource<Movie>> PopularMovieCallback(CollectionAdapter.ViewHolder holder, Context context) {
        return new retrofit2.Callback<FilmResource<Movie>>() {
            @Override
            public void onResponse(retrofit2.Call<FilmResource<Movie>> call, retrofit2.Response<FilmResource<Movie>> response) {
                FilmResource<Movie> movieFilmResource = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                holder.recycleviewMovie.setLayoutManager(layoutManager);

                if (movieFilmResource != null) {
                    MovieAdapter movieAdapter = new MovieAdapter(context, movieFilmResource.getResults());
                    movieAdapter.setFilmClickListener(new FilmClickListener() {
                        @Override
                        public void onClickItemMovie(Movie movie) {
                            Intent intent = new Intent(context, MovieActivity.class);
                            intent.putExtra("id",movie.getId());
                            intent.putExtra("media_type","movie");
                            context.startActivity(intent);
                        }

                        @Override
                        public void onClickItemTvSerie(TvSerie tvSerie) {

                        }
                    });
                    holder.recycleviewMovie.setAdapter(movieAdapter);
                } else {
                    MovieAdapter movieAdapter = new MovieAdapter(context, new ArrayList<>());
                    holder.recycleviewMovie.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FilmResource<Movie>> call, Throwable t) {

            }
        };
    }
    public static Callback<FilmResource<TvSerie>> PopularTvSeriesCallback(CollectionAdapter.ViewHolder holder, Context context) {
        return new Callback<FilmResource<TvSerie>>() {
            @Override
            public void onResponse(Call<FilmResource<TvSerie>> call, Response<FilmResource<TvSerie>> response) {
                FilmResource<TvSerie> tvSerieFilmResource = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
                holder.recycleviewMovie.setLayoutManager(layoutManager);

                if (tvSerieFilmResource != null) {
                    TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(context,tvSerieFilmResource.getResults());
                    tvSerieAdapter.setFilmClickListener(new FilmClickListener() {
                        @Override
                        public void onClickItemMovie(Movie movie) {
                        }

                        @Override
                        public void onClickItemTvSerie(TvSerie tvSerie) {
                            Intent intent = new Intent(context, MovieActivity.class);
                            intent.putExtra("id",tvSerie.getId());
                            intent.putExtra("media_type","tv");
                            context.startActivity(intent);
                        }
                    });
                    holder.recycleviewMovie.setAdapter(tvSerieAdapter);
                }
                else {
                    TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(context,new ArrayList<>());
                    holder.recycleviewMovie.setAdapter(tvSerieAdapter);
                }
            }

            @Override
            public void onFailure(Call<FilmResource<TvSerie>> call, Throwable t) {

            }
        };
    }
    public static Callback<ImageType> SliderCallBack(ActivityMovieBinding binding) {
        return new Callback<ImageType>() {
            @Override
            public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                ImageType imageType = response.body();

                List<SlideModel> imageList = new ArrayList(); // Create image list


                if (imageType.getBackdrops().size() < 3) {
                    for (int i = 0; i < imageType.getBackdrops().size(); i++) {
                        imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(i).getFile_path(), "",ScaleTypes.CENTER_CROP));
                    }
//                    imageList.add(new SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years.", ScaleTypes.CENTER_CROP));
//                    imageList.add(new SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct.",ScaleTypes.CENTER_CROP));
//                    imageList.add(new SlideModel("https://bit.ly/3fLJf72", "And people do that.",ScaleTypes.CENTER_CROP));
                }
                else {
                    imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(0).getFile_path(), "",ScaleTypes.CENTER_CROP));
                    imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(1).getFile_path(), "",ScaleTypes.CENTER_CROP));
                    imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(2).getFile_path(), "",ScaleTypes.CENTER_CROP));
                }

                ImageSlider imageSlider = binding.contentFilm.imageSlider;
                imageSlider.setImageList(imageList);

            }

            @Override
            public void onFailure(Call<ImageType> call, Throwable t) {

            }
        };
    }
    public static Callback<TvSerieInfo> TvDetailsCallBack(Context context, ActivityMovieBinding binding, ExtraInfoListener extraInfoListener) {
        return new Callback<TvSerieInfo>() {
            @Override
            public void onResponse(Call<TvSerieInfo> call, Response<TvSerieInfo> response) {
                TvSerieInfo tvSerieInfo = response.body();
                String no_expisode = tvSerieInfo.getNumber_of_episodes();
                if (tvSerieInfo.isIn_production()) {
                    no_expisode = no_expisode + " - ";
                }
                binding.contentFilm.tvMovieEp.setText(no_expisode);
                binding.contentFilm.tvFilmOverview.setText(tvSerieInfo.getOverview());
                binding.contentFilm.tvFilmName.setText(tvSerieInfo.getName());
                if (tvSerieInfo.getName().length() < 11){
                    binding.toolbarTitle.setText(tvSerieInfo.getName());
                } else {
                    String toolbar_title = "";
                    String tv_name = tvSerieInfo.getName();
                    for (int i = 0; i <= 10; i++) {
                        toolbar_title = toolbar_title + tv_name.charAt(i);
                    }
                    binding.toolbarTitle.setText(toolbar_title + "...");
                }
                binding.contentFilm.tvMovieRate.setText(Double.toString(tvSerieInfo.getVote_average()));
                binding.contentFilm.tvMovieDate.setText(tvSerieInfo.getFirst_air_date());

                Glide.with(context)
                        .load("https://image.tmdb.org/t/p/w500" + tvSerieInfo.getPoster_path())
                        .into(binding.contentFilm.imgMovie);

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false);
                binding.contentFilm.recycleviewGenres.setLayoutManager(layoutManager);

                GenresAdapter genresAdapter = new GenresAdapter(tvSerieInfo.getGenres());
                binding.contentFilm.recycleviewGenres.setAdapter(genresAdapter);


                LinearLayoutManager extra_info_layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                binding.contentFilm.recycleviewExtraInfo.setLayoutManager(extra_info_layoutManager);

                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                binding.contentFilm.recycleviewExtraInfo.addItemDecoration(dividerItemDecoration);

                List<ExtraInfo> extraInfoList = new ArrayList<>();
                extraInfoList.add(new ExtraInfo(tvSerieInfo.getId(),"Các tập phim",tvSerieInfo.getNumber_of_episodes() + " tập","Session"));
                if (tvSerieInfo.isIn_production()){
                    extraInfoList.add(new ExtraInfo(tvSerieInfo.getId(),"Tập phim tiếp theo",tvSerieInfo.getNext_episode_to_air().getAir_date(),"Next Ep"));
                }

                TvExtraInfoAdapter tvExtraInfoAdapter = new TvExtraInfoAdapter(context,extraInfoList);
                tvExtraInfoAdapter.setExtraInfoListener(extraInfoListener);

                binding.contentFilm.recycleviewExtraInfo.setAdapter(tvExtraInfoAdapter);



            }

            @Override
            public void onFailure(Call<TvSerieInfo> call, Throwable t) {

            }
        };
    }
    public static Callback<MovieInfo> MovieDetailCallBack(Context context, ActivityMovieBinding binding) {
        return new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                MovieInfo movieInfo = response.body();

                binding.contentFilm.tvMovieEp.setText(movieInfo.getRuntime()+" phút");
                binding.contentFilm.tvFilmOverview.setText(movieInfo.getOverview());
                binding.contentFilm.tvFilmName.setText(movieInfo.getTitle());
                if (movieInfo.getTitle().length() < 11){
                    binding.toolbarTitle.setText(movieInfo.getTitle());
                } else {
                    String toolbar_title = "";
                    String tv_name = movieInfo.getTitle();
                    for (int i = 0; i <= 10; i++) {
                        toolbar_title = toolbar_title + tv_name.charAt(i);
                    }
                    binding.toolbarTitle.setText(toolbar_title + "...");
                }
                binding.contentFilm.tvMovieRate.setText(Double.toString(movieInfo.getVote_average()));
                binding.contentFilm.tvMovieDate.setText(movieInfo.getRelease_date());

                Glide.with(context)
                        .load("https://image.tmdb.org/t/p/w500" + movieInfo.getPoster_path())
                        .into(binding.contentFilm.imgMovie);

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false);
                binding.contentFilm.recycleviewGenres.setLayoutManager(layoutManager);

                GenresAdapter genresAdapter = new GenresAdapter(movieInfo.getGenres());
                binding.contentFilm.recycleviewGenres.setAdapter(genresAdapter);
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {

            }
        };
    }
    public static Callback<CreditsResource> TvCreditCallBack(Context context, ActivityMovieBinding binding) {
        return new Callback<CreditsResource>() {
            @Override
            public void onResponse(Call<CreditsResource> call, Response<CreditsResource> response) {
                CreditsResource creditsResource = response.body();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false);
                binding.contentFilm.recycleviewCast.setLayoutManager(layoutManager);

                CastAdapter castAdapter = new CastAdapter(context,creditsResource.getCast());
                binding.contentFilm.recycleviewCast.setAdapter(castAdapter);

                LinearLayoutManager layoutManager_crew = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
                binding.contentFilm.recycleviewCrew.setLayoutManager(layoutManager_crew);
                CrewAdapter crewAdapter = new CrewAdapter(new String[]{ "Quản lý" , "Soạn thảo" },creditsResource.getCrew());
                binding.contentFilm.recycleviewCrew.setAdapter(crewAdapter);
            }

            @Override
            public void onFailure(Call<CreditsResource> call, Throwable t) {

            }
        };
    }
    public static Callback<ImageType> TvImageCallBack(Context context, ActivityMovieBinding binding) {
        return new Callback<ImageType>() {
            @Override
            public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                ImageType imageType = response.body();


                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false);
                binding.contentFilm.recycleviewImageMovie.setLayoutManager(layoutManager);

                AlbumAdapter albumAdapter = new AlbumAdapter(context,imageType.getBackdrops());
                binding.contentFilm.recycleviewImageMovie.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<ImageType> call, Throwable t) {

            }
        };
    }
    public static Callback<FilmResource<TvSerie>> TvRecommendCallBack(Context context, ActivityMovieBinding binding,FilmClickListener RecommendedTv) {
        return new Callback<FilmResource<TvSerie>>() {
            @Override
            public void onResponse(Call<FilmResource<TvSerie>> call, Response<FilmResource<TvSerie>> response) {

                FilmResource<TvSerie> tvSerieFilmResource = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
                binding.contentFilm.recycleviewRecommend.setLayoutManager(layoutManager);

                if (tvSerieFilmResource != null) {
                    TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(context,tvSerieFilmResource.getResults());
                    tvSerieAdapter.setFilmClickListener(RecommendedTv);
                    binding.contentFilm.recycleviewRecommend.setAdapter(tvSerieAdapter);
                }
                else {
                    TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(context,new ArrayList<>());
                    binding.contentFilm.recycleviewRecommend.setAdapter(tvSerieAdapter);
                }
            }


            @Override
            public void onFailure(Call<FilmResource<TvSerie>> call, Throwable t) {

            }
        };
    }
    public static Callback<FilmResource<Movie>> MovieRecommendCallback(Context context, ActivityMovieBinding binding,FilmClickListener RecommendedMovie) {
        return new Callback<FilmResource<Movie>>() {
            @Override
            public void onResponse(Call<FilmResource<Movie>> call, Response<FilmResource<Movie>> response) {
                FilmResource<Movie> movieFilmResource = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                binding.contentFilm.recycleviewRecommend.setLayoutManager(layoutManager);

                if (movieFilmResource != null) {
                    MovieAdapter movieAdapter = new MovieAdapter(context, movieFilmResource.getResults());
                    movieAdapter.setFilmClickListener(RecommendedMovie);
                    binding.contentFilm.recycleviewRecommend.setAdapter(movieAdapter);
                } else {
                    MovieAdapter movieAdapter = new MovieAdapter(context, new ArrayList<>());
                    binding.contentFilm.recycleviewRecommend.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<FilmResource<Movie>> call, Throwable t) {

            }
        };
    }
    public static Callback<FilmResource<Movie>> PopularMoviePagnateCallBack(Context context, ActivityPaginateBinding binding, FilmClickListener filmClickListener) {
        return new Callback<FilmResource<Movie>>() {

            private MoviePagnateAdapter moviePagnateAdapter;
            private FilmResource<Movie> movieFilmResource;
            private int count = 0;
            private int page = 1;

            @Override
            public void onResponse(Call<FilmResource<Movie>> call, Response<FilmResource<Movie>> response) {
                movieFilmResource = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                binding.recycleviewPagnate.setLayoutManager(layoutManager);

                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                binding.recycleviewPagnate.addItemDecoration(dividerItemDecoration);

                moviePagnateAdapter = new MoviePagnateAdapter(context,movieFilmResource.getResults());
                moviePagnateAdapter.setFilmClickListener(filmClickListener);
                binding.recycleviewPagnate.setAdapter(moviePagnateAdapter);

                binding.recycleviewPagnate.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        if (dy > 0){

                            Toast.makeText(context, "" +dy, Toast.LENGTH_LONG).show();
                            if (dy > 500) {
                                page++;
                                Retrofit.retrofit.getPopularMovie("vi-Vn", page)
                                        .enqueue(new Callback<FilmResource<Movie>>() {
                                            @Override
                                            public void onResponse(Call<FilmResource<Movie>> call, Response<FilmResource<Movie>> response) {
                                                for (int i = 0; i < response.body().getResults().size(); i++) {
                                                    movieFilmResource.getResults().add(response.body().getResults().get(i));
                                                }

                                                moviePagnateAdapter = new MoviePagnateAdapter(context,movieFilmResource.getResults());
                                                moviePagnateAdapter.setFilmClickListener(filmClickListener);

                                                binding.recycleviewPagnate.setAdapter(moviePagnateAdapter);
                                            }

                                            @Override
                                            public void onFailure(Call<FilmResource<Movie>> call, Throwable t) {

                                            }
                                        });
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<FilmResource<Movie>> call, Throwable t) {

            }
        };
    }
    public static Callback<FilmResource<TvSerie>> PopularTvPagnateCallBack(Context context, ActivityPaginateBinding binding, FilmClickListener filmClickListener) {
        return new Callback<FilmResource<TvSerie>>() {
            private TvPagnateAdapter tvPagnateAdapter;
            private FilmResource<TvSerie> tvSerieFilmResource;
            private int page = 1;
            private boolean isLoading;
            private boolean isLastPage;
            private int totalPage = 5;
            private int currentPage = 1;

            @Override
            public void onResponse(Call<FilmResource<TvSerie>> call, Response<FilmResource<TvSerie>> response) {
                tvSerieFilmResource = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                binding.recycleviewPagnate.setLayoutManager(layoutManager);

                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                binding.recycleviewPagnate.addItemDecoration(dividerItemDecoration);

                tvPagnateAdapter = new TvPagnateAdapter(context,tvSerieFilmResource.getResults());
                tvPagnateAdapter.setFilmClickListener(filmClickListener);
                binding.recycleviewPagnate.setAdapter(tvPagnateAdapter);


                if (currentPage < totalPage){
                    tvPagnateAdapter.addFooterLoading();
                }
                else {
                    isLastPage = true;
                }
                binding.recycleviewPagnate.setOnScrollListener(new PaginationScrollListener(layoutManager) {
                    @Override
                    public void loadMoreItems() {
                        isLoading = true;

                        currentPage += 1;
                        loadNextPage();
                    }
                    @Override
                    public boolean isLoading() {
                        return isLoading;
                    }
                    @Override
                    public boolean isLastPage() {
                        return isLastPage;
                    }
                });
            }
            private void loadNextPage() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Load data page " + currentPage,Toast.LENGTH_LONG).show();
                        Retrofit.retrofit.getPopularTvSeries("en", currentPage)
                            .enqueue(new Callback<FilmResource<TvSerie>>() {
                                @Override
                                public void onResponse(Call<FilmResource<TvSerie>> call, Response<FilmResource<TvSerie>> response) {
                                    List<TvSerie> list = response.body().getResults();

                                    tvPagnateAdapter.removeFooterLoading();

                                    tvSerieFilmResource.getResults().addAll(list);

                                    tvPagnateAdapter.notifyDataSetChanged();

                                    isLoading = false;

                                    if (currentPage < totalPage){
                                        tvPagnateAdapter.addFooterLoading();
                                    }
                                    else {
                                        isLastPage = true;
                                    }
                                }
                                @Override
                                public void onFailure(Call<FilmResource<TvSerie>> call, Throwable t) {

                                }
                            });
                    }
                },4000);
            }


            @Override
            public void onFailure(Call<FilmResource<TvSerie>> call, Throwable t) {

            }
        };
    }
}
