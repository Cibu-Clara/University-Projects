from django.db import models


class Song(models.Model):
    song_name = models.CharField(max_length=100)
    label = models.CharField(max_length=100)
    genre = models.CharField(max_length=100)
    year_of_release = models.PositiveSmallIntegerField()
    duration = models.CharField(max_length=10)

    def __str__(self):
        return self.song_name


class Artist(models.Model):
    CNP = models.CharField(max_length=14, unique=True)
    artist_name = models.CharField(max_length=100)
    real_name = models.CharField(max_length=100)
    country = models.CharField(max_length=100)
    email = models.CharField(max_length=100)

    def __str__(self):
        return self.artist_name


class Album(models.Model):
    album_title = models.CharField(max_length=100)
    nr_of_tracks = models.IntegerField()
    label = models.CharField(max_length=100)
    year_of_release = models.IntegerField()
    main_artist = models.ForeignKey(Artist, on_delete=models.CASCADE, related_name='albums')

    def __str__(self):
        return self.album_title
