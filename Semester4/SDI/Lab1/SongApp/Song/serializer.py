from rest_framework import serializers
from Song.models import Song


class SongSerializer(serializers.ModelSerializer):
    class Meta:
        model = Song
        fields = "__all__"