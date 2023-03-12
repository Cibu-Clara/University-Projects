from django.urls import path
from Song import views

urlpatterns = [
    path('', views.SongList.as_view()),
    path("<int:id>/", views.SongInfo.as_view())
]