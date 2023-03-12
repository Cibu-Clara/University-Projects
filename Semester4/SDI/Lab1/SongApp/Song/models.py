from django.db import models


class Song(models.Model):
    artist = models.CharField(max_length=100)
    song_name = models.CharField(max_length=100)
    label = models.CharField(max_length=100)
    genre = models.CharField(max_length=100)
    year_of_release = models.PositiveSmallIntegerField()
