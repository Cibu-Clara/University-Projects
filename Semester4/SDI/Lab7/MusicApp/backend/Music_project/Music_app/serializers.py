import datetime
from rest_framework import serializers
from .models import *
import re


class DynamicFieldsModelSerializer(serializers.ModelSerializer):
    """
    A ModelSerializer that takes an additional `fields` argument that
    controls which fields should be displayed.
    """

    def __init__(self, *args, **kwargs):
        kwargs.pop('fields', None)
        include_fields = kwargs.pop('include_fields', None)
        exclude_fields = kwargs.pop('exclude_fields', None)

        # Instantiate the superclass normally
        super().__init__(*args, **kwargs)

        if include_fields is not None:
            for field in include_fields:
                self.fields.append(field)
        if exclude_fields is not None:
            for field in exclude_fields:
                split = field.split('__')
                to_access = self.fields
                for i in range(len(split)-1):
                    to_access = to_access.get(split[i])
                if isinstance(to_access, serializers.ListSerializer):
                    to_access = to_access.child
                to_access.fields.pop(split[-1])


class ArtistSerializer(DynamicFieldsModelSerializer):
    """
    """
    artist_name = serializers.CharField(max_length=100)
    real_name = serializers.CharField(max_length=100)
    country = serializers.CharField(max_length=100)
    email = serializers.EmailField(max_length=100)
    nr_albums = serializers.IntegerField(read_only=True)
    songs = Song()

    def validate_email(self, value):
        existing_emails = Artist.objects.filter(email=value)

        if self.instance:
            existing_emails = existing_emails.exclude(pk=self.instance.pk)
        if existing_emails.exists():
            raise serializers.ValidationError("This email address is already in use!")
        return value

    class Meta:
        model = Artist
        fields = ('id', 'artist_name', 'real_name', 'country', 'email', 'nr_albums', 'songs')
        ordering = ['id']


class SongSerializer(DynamicFieldsModelSerializer):
    """
    """
    song_name = serializers.CharField(max_length=100)
    composer = serializers.CharField(max_length=100)
    genre = serializers.CharField(max_length=100)
    year_of_release = serializers.IntegerField()
    artists = ArtistSerializer(many=True, read_only=True)

    def validate_year_of_release(self, value):
        today = datetime.datetime.now()
        year = today.year
        if value > year or value < 1800:
            raise serializers.ValidationError("Not a valid year!")
        return value

    class Meta:
        model = Song
        fields = (
            'id', 'song_name', 'composer', 'genre', 'year_of_release', 'artists')
        ordering = ['id']


class AlbumSerializer(DynamicFieldsModelSerializer):
    """
    """
    album_title = serializers.CharField(max_length=100)
    nr_of_tracks = serializers.IntegerField()
    label = serializers.CharField(max_length=100)
    year_of_release = serializers.IntegerField()
    main_artist = ArtistSerializer(read_only=True)
    main_artist_id = serializers.IntegerField(write_only=True)

    def validate_nr_of_tracks(self, value):
        if value <= 0:
            raise serializers.ValidationError("No. of tracks must be greater than zero!")
        return value

    def validate_year_of_release(self, value):
        today = datetime.datetime.now()
        year = today.year
        if value > year or value < 1800:
            raise serializers.ValidationError("Not a valid year!")
        return value

    class Meta:
        model = Album
        fields = "__all__"
        ordering = ['id']


class AlbumSerializerID(DynamicFieldsModelSerializer):
    album_title = serializers.CharField(max_length=100)
    nr_of_tracks = serializers.IntegerField()
    label = serializers.CharField(max_length=100)
    year_of_release = serializers.IntegerField()
    main_artist = ArtistSerializer()

    def validate_nr_of_tracks(self, value):
        if value <= 0:
            raise serializers.ValidationError("No. of tracks must be greater than zero!")
        return value

    def validate_year_of_release(self, value):
        today = datetime.datetime.now()
        year = today.year
        if value > year or value < 1800:
            raise serializers.ValidationError("Not a valid year!")
        return value

    class Meta:
        model = Album
        fields = "__all__"
        depth = 1


class PerformsOnSerializer(DynamicFieldsModelSerializer):
    """
    """
    song = SongSerializer(read_only=True)
    artist = ArtistSerializer(read_only=True)
    nr_of_views = serializers.IntegerField()
    duration = serializers.CharField(max_length=10)
    song_id = serializers.IntegerField(write_only=True)
    artist_id = serializers.IntegerField(write_only=True)

    def validate_duration(self, value):
        pattern = r'^\d{2}:\d{2}$'
        if not re.match(pattern, value):
            raise serializers.ValidationError("Invalid duration format! Expected format: MM:SS")
        return value

    def validate_nr_of_views(self, value):
        if value < 0:
            raise serializers.ValidationError("No. of views must be greater than or equal to zero!")
        return value

    class Meta:
        model = PerformsOn
        fields = "__all__"
        ordering = ['id']


class PerformsOnSerializerID(DynamicFieldsModelSerializer):
    song = SongSerializer()
    artist = ArtistSerializer()
    nr_of_views = serializers.IntegerField()
    duration = serializers.CharField(max_length=10)

    class Meta:
        model = PerformsOn
        fields = "__all__"
        depth = 1


class ArtistViewsStatisticsSerializer(DynamicFieldsModelSerializer):
    """
    """
    artist_name = serializers.CharField(max_length=100)
    real_name = serializers.CharField(max_length=100)
    country = serializers.CharField(max_length=100)
    email = serializers.EmailField(max_length=100)
    avg_views = serializers.IntegerField(read_only=True)

    class Meta:
        model = Artist
        fields = ['artist_name', 'real_name', 'country', 'email', 'avg_views']
        depth = 1


class SongsNumberStatisticsSerializer(DynamicFieldsModelSerializer):
    """
    """
    song_name = serializers.CharField(max_length=100)
    composer = serializers.CharField(max_length=100)
    genre = serializers.CharField(max_length=100)
    year_of_release = serializers.IntegerField()
    no_of_performances = serializers.IntegerField(read_only=True)

    class Meta:
        model = Song
        fields = ['song_name', 'composer', 'genre', 'year_of_release', 'no_of_performances']
        depth = 1
