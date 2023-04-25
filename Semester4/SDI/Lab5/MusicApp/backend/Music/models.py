import datetime

from django.db import models
from django.core.validators import MinValueValidator, MaxValueValidator, RegexValidator


class Song(models.Model):
    song_name = models.CharField(max_length=100)
    composer = models.CharField(max_length=100)
    genre = models.CharField(max_length=100)
    today = datetime.datetime.now()
    year = today.year
    year_of_release = models.IntegerField(validators=[MinValueValidator(0), MaxValueValidator(year)])
    artists = models.ManyToManyField('Artist', through='PerformsOn')

    def __str__(self):
        return self.song_name


class Artist(models.Model):
    artist_name = models.CharField(max_length=100)
    real_name = models.CharField(max_length=100)
    country = models.CharField(max_length=100)
    email = models.EmailField(max_length=100)
    songs = models.ManyToManyField('Song', through='PerformsOn')

    def __str__(self):
        return self.artist_name


class Album(models.Model):
    album_title = models.CharField(max_length=100)
    nr_of_tracks = models.IntegerField()
    label = models.CharField(max_length=100)
    today = datetime.datetime.now()
    year = today.year
    year_of_release = models.PositiveSmallIntegerField(validators=[MinValueValidator(1800), MaxValueValidator(year)])
    main_artist = models.ForeignKey(Artist, on_delete=models.CASCADE, related_name='albums')

    def __str__(self):
        return self.album_title


class PerformsOn(models.Model):
    song = models.ForeignKey(Song, on_delete=models.DO_NOTHING)
    artist = models.ForeignKey(Artist, on_delete=models.DO_NOTHING)
    nr_of_views = models.IntegerField()
    duration = models.CharField(max_length=10, validators=[RegexValidator("..:..")])

    def __str__(self):
        return self.artist.artist_name + " - " + self.song.song_name
