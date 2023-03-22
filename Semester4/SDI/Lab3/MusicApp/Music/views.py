from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from Music.models import Song, Artist, Album, PerformsOn
from Music.serializer import SongSerializer, ArtistSerializer, AlbumSerializer, AlbumSerializerID, \
    PerformsOnSerializer, PerformsOnSerializerID, ArtistViewsStatisticsSerializer, SongsNumberStatisticsSerializer
from django.db.models import Avg, Count


class SongList(APIView):
    def get(self, request):
        songs = Song.objects.all()
        serializer = SongSerializer(songs, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = SongSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class SongInfo(APIView):
    def get(self, request, id):
        try:
            obj = Song.objects.get(id=id)

        except Song.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = SongSerializer(obj)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def put(self, request, id):
        try:
            obj = Song.objects.get(id=id)

        except Song.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = SongSerializer(obj, data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_205_RESET_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def patch(self, request, id):
        try:
            obj = Song.objects.get(id=id)

        except Song.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = SongSerializer(obj, data=request.data, partial=True)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_205_RESET_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, id):
        try:
            obj = Song.objects.get(id=id)

        except Song.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)


class ArtistList(APIView):
    def get(self, request):
        artists = Artist.objects.all()
        serializer = ArtistSerializer(artists, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = ArtistSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class ArtistInfo(APIView):
    def get(self, request, id):
        try:
            obj = Artist.objects.get(id=id)
            serialized_artist = ArtistSerializer(obj)
            serialized_albums = AlbumSerializer(obj.albums.all(), many=True)

            serialized_artist_data = serialized_artist.data
            serialized_artist_data['albums'] = serialized_albums.data

            for i in range(len(serialized_artist_data['albums'])):
                del serialized_artist_data['albums'][i]['main_artist']

        except Artist.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        return Response(serialized_artist_data, status=status.HTTP_200_OK)

    def put(self, request, id):
        try:
            obj = Artist.objects.get(id=id)

        except Artist.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = ArtistSerializer(obj, data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_205_RESET_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def patch(self, request, id):
        try:
            obj = Artist.objects.get(id=id)

        except Artist.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = ArtistSerializer(obj, data=request.data, partial=True)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_205_RESET_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, id):
        try:
            obj = Artist.objects.get(id=id)

        except Artist.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)


class AlbumList(APIView):
    def get(self, request):
        albums = Album.objects.all()
        serializer = AlbumSerializer(albums, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = AlbumSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class AlbumInfo(APIView):
    def get(self, request, id):
        try:
            obj = Album.objects.get(id=id)

        except Album.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = AlbumSerializerID(obj)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def put(self, request, id):
        try:
            obj = Album.objects.get(id=id)

        except Album.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = AlbumSerializer(obj, data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_205_RESET_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def patch(self, request, id):
        try:
            obj = Album.objects.get(id=id)

        except Album.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = AlbumSerializer(obj, data=request.data, partial=True)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_205_RESET_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, id):
        try:
            obj = Album.objects.get(id=id)

        except Album.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)


class SongFilterView(APIView):
    def get(self, request, year):
        songs = Song.objects.filter(year_of_release__gt=year)
        serializer = SongSerializer(songs, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)


class PerformsOnList(APIView):
    def get(self, request):
        performances = PerformsOn.objects.all()
        serializer = PerformsOnSerializer(performances, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = PerformsOnSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class PerformsOnInfo(APIView):
    def get(self, request, id):
        try:
            obj = PerformsOn.objects.get(id=id)

        except PerformsOn.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = PerformsOnSerializerID(obj)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def put(self, request, id):
        try:
            obj = PerformsOn.objects.get(id=id)

        except PerformsOn.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = PerformsOnSerializer(obj, data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_205_RESET_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def patch(self, request, id):
        try:
            obj = PerformsOn.objects.get(id=id)

        except PerformsOn.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = PerformsOnSerializer(obj, data=request.data, partial=True)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_205_RESET_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, id):
        try:
            obj = PerformsOn.objects.get(id=id)

        except PerformsOn.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)


class ArtistViewsStatistics(APIView):
    def get(self, request):
        # artists ordered by the average of their performances' views
        statistics = Artist.objects.annotate(
            avg_views=Avg('performson__nr_of_views')
        ).order_by('-avg_views')

        serializer = ArtistViewsStatisticsSerializer(statistics, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)


class SongsNumberStatistics(APIView):
    def get(self, request):
        # songs ordered by the number of performances
        statistics = Song.objects.annotate(
            nr_performances=Count('performson__song_id')
        ).order_by('-nr_performances')

        serializer = SongsNumberStatisticsSerializer(statistics, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)