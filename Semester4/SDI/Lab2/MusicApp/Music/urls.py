from django.urls import path
from Music import views

urlpatterns = [
    path("songs/", views.SongList.as_view(), name="songs"),
    path("songs/<int:id>/", views.SongInfo.as_view()),
    path("artists/", views.ArtistList.as_view(), name="artists"),
    path("artists/<int:id>/", views.ArtistInfo.as_view()),
    path("albums/", views.AlbumList.as_view(), name="albums"),
    path("albums/<int:id>/", views.AlbumInfo.as_view()),
    path("filter-songs/<int:year>/", views.SongFilterView.as_view())
]