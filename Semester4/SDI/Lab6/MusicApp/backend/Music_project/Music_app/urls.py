from django.urls import path
from .views import *
urlpatterns = [
    path("songs/", SongList.as_view(), name="songs"),
    path("songs/<int:id>/", SongInfo.as_view()),
    path("songs/filter-by-year/<int:year>/", SongFilterView.as_view()),
    path("songs/order-by-performances/", SongsNumberStatistics.as_view()),
    path("artists/", ArtistList.as_view(), name="artists"),
    path("artists/<int:id>/", ArtistInfo.as_view()),
    path("artists/order-by-views/", ArtistViewsStatistics.as_view()),
    path("albums/", AlbumList.as_view(), name="albums"),
    path("albums/<int:id>/", AlbumInfo.as_view()),
    path("performances/", PerformsOnList.as_view(), name="performances"),
    path("performances/<int:id>/", PerformsOnInfo.as_view()),
]