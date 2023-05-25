from django.urls import path
from .views import *
from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView

urlpatterns = [
    path("songs/", SongListCreateView.as_view(), name="songs"),
    path("songs/<int:id>/", SongInfo.as_view()),
    path("songs/filter-by-year/<int:year>/", SongFilterView.as_view(), name="year"),
    path("songs/order-by-performances/", SongsOrderedByNoOfPerformances.as_view()),
    path("songs/order-by-name/<str:song_name>/", SongsOrderedByName.as_view(), name="song_name"),

    path("artists/", ArtistListCreateView.as_view(), name="artists"),
    path("artists/<int:id>/", ArtistInfo.as_view()),
    path("artists/order-by-views/", ArtistsOrderedByAverageNoOfViews.as_view()),
    path("artists/order-by-name/<str:artist_name>/", ArtistsOrderedByName.as_view(), name="artist_name"),

    path("albums/", AlbumListCreateView.as_view(), name="albums"),
    path("albums/<int:id>/", AlbumInfo.as_view()),

    path("performances/", PerformsOnListCreateView.as_view(), name="performances"),
    path("performances/<int:id>/", PerformsOnInfo.as_view()),

    path("login/", LoginView.as_view(), name="token_obtain_pair"),
    path("login/refresh/", TokenRefreshView.as_view(), name="token_refresh"),
    path("register/", UserRegistrationView.as_view(), name="register"),
    path("activate/", UserActivationView.as_view(), name="activate_user"),
    path("profile/<str:id>/", UserInfo.as_view(), name="profile"),
]