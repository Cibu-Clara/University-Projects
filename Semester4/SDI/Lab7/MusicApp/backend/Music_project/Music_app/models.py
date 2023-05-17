from django.db import models


class Song(models.Model):
    song_name = models.CharField(max_length=100)
    composer = models.CharField(max_length=100)
    genre = models.CharField(max_length=100)
    year_of_release = models.IntegerField()
    artists = models.ManyToManyField('Artist', through='PerformsOn')

    class Meta:
        ordering = ['id']

    def __str__(self):
        return self.song_name


class Artist(models.Model):
    artist_name = models.CharField(max_length=100)
    real_name = models.CharField(max_length=100)
    country = models.CharField(max_length=100)
    email = models.EmailField(max_length=100)
    songs = models.ManyToManyField('Song', through='PerformsOn')

    class Meta:
        ordering = ['id']

    def __str__(self):
        return self.artist_name


class Album(models.Model):
    album_title = models.CharField(max_length=100)
    nr_of_tracks = models.IntegerField()
    label = models.CharField(max_length=100)
    year_of_release = models.PositiveSmallIntegerField()
    main_artist = models.ForeignKey(Artist, on_delete=models.CASCADE, related_name='albums')

    class Meta:
        ordering = ['id']

    def __str__(self):
        return self.album_title


class PerformsOn(models.Model):
    song = models.ForeignKey(Song, on_delete=models.CASCADE)
    artist = models.ForeignKey(Artist, on_delete=models.CASCADE)
    nr_of_views = models.IntegerField()
    duration = models.CharField(max_length=10)

    class Meta:
        ordering = ['id']

    def __str__(self):
        return self.artist.artist_name + " - " + self.song.song_name