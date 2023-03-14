from rest_framework import serializers
from Music.models import Song, Artist, Album


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

