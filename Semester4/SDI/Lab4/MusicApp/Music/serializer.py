from rest_framework import serializers
from Music.models import Song, Artist, Album, PerformsOn


class SongSerializer(serializers.ModelSerializer):
    class Meta:
        model = Song
        fields = "__all__"


class ArtistSerializer(serializers.ModelSerializer):
    class Meta:
        model = Artist
        fields = "__all__"


class AlbumSerializer(serializers.ModelSerializer):
    album_title = serializers.CharField(max_length=100)
    nr_of_tracks = serializers.IntegerField()
    label = serializers.CharField(max_length=100)
    year_of_release = serializers.IntegerField()
    main_artist = Artist()

    class Meta:
        model = Album
        fields = "__all__"


class AlbumSerializerID(serializers.ModelSerializer):
    album_title = serializers.CharField(max_length=100)
    nr_of_tracks = serializers.IntegerField()
    label = serializers.CharField(max_length=100)
    year_of_release = serializers.IntegerField()
    main_artist = Artist()

    class Meta:
        model = Album
        fields = "__all__"
        depth = 1


class PerformsOnSerializer(serializers.ModelSerializer):
    song_id = Song()
    artist_id = Artist()
    nr_of_views = serializers.IntegerField()
    duration = serializers.CharField(max_length=10)

    class Meta:
        model = PerformsOn
        fields = "__all__"


class PerformsOnSerializerID(serializers.ModelSerializer):
    song_id = Song()
    artist_id = Artist()
    nr_of_views = serializers.IntegerField()
    duration = serializers.CharField(max_length=10)

    class Meta:
        model = PerformsOn
        fields = "__all__"
        depth = 1


class ArtistViewsStatisticsSerializer(serializers.ModelSerializer):
    avg_views = serializers.IntegerField(read_only=True)

    class Meta:
        model = Artist
        fields = ['artist_name', 'avg_views']


class SongsNumberStatisticsSerializer(serializers.ModelSerializer):
    nr_performances = serializers.IntegerField(read_only=True)

    class Meta:
        model = Song
        fields = ['song_name', 'nr_performances']
