from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import *
from django.db.models import Avg, Count
from drf_spectacular.utils import extend_schema


class SongList(APIView):
    @extend_schema(responses=SongSerializer)
    def get(self, request):
        songs = Song.objects.all()
        serializer = SongSerializer(songs, many=True, exclude_fields=['artists'])
        return Response(serializer.data)

    @extend_schema(responses=SongSerializer)
    def post(self, request):
        serializer = SongSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class SongInfo(APIView):
    @extend_schema(responses=SongSerializer)
    def get(self, request, id):
        try:
            obj = Song.objects.get(id=id)

        except Song.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = SongSerializer(obj, exclude_fields=['artists__songs', 'artists__albums'])
        return Response(serializer.data, status=status.HTTP_200_OK)

    @extend_schema(responses=SongSerializer)
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

    @extend_schema(responses=SongSerializer)
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

    @extend_schema(responses=SongSerializer)
    def delete(self, request, id):
        try:
            obj = Song.objects.get(id=id)

        except Song.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)


class ArtistList(APIView):
    @extend_schema(responses=ArtistSerializer)
    def get(self, request):
        artists = Artist.objects.all()
        serializer = ArtistSerializer(artists, many=True, exclude_fields=['songs', 'albums'])
        return Response(serializer.data)

    @extend_schema(responses=ArtistSerializer)
    def post(self, request):
        serializer = ArtistSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class ArtistInfo(APIView):
    @extend_schema(responses=ArtistSerializer)
    def get(self, request, id):
        try:
            obj = Artist.objects.get(id=id)
            serialized_artist = ArtistSerializer(obj)
            serialized_songs = SongSerializer(obj.songs.all(), many=True, exclude_fields=['artists'])
            serialized_albums = AlbumSerializer(obj.albums.all(), many=True)

            serialized_artist_data = serialized_artist.data
            serialized_artist_data['songs'] = serialized_songs.data
            serialized_artist_data['albums'] = serialized_albums.data

            for i in range(len(serialized_artist_data['albums'])):
                del serialized_artist_data['albums'][i]['main_artist']

        except Artist.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        return Response(serialized_artist_data, status=status.HTTP_200_OK)

    def post(self, request, id):
        data = request.data
        datastr = data['data']
        datastr = datastr.split(",")
        for datas in datastr:
            cast = int(datas)
            if Album.objects.get(id=cast):
                obj = Album.objects.all()
                Album.objects.create(main_artist=Artist.objects.get(id=id),
                                     album_title=obj.get(id=cast).album_title,
                                     nr_of_tracks=obj.get(id=cast).nr_of_tracks,
                                     label=obj.get(id=cast).label,
                                     year_of_release=obj.get(id=cast).year_of_release)
        return Response("success")

    @extend_schema(responses=ArtistSerializer)
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

    @extend_schema(responses=ArtistSerializer)
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

    @extend_schema(responses=ArtistSerializer)
    def delete(self, request, id):
        try:
            obj = Artist.objects.get(id=id)

        except Artist.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)


class AlbumList(APIView):
    @extend_schema(responses=AlbumSerializer)
    def get(self, request):
        albums = Album.objects.all()
        serializer = AlbumSerializer(albums, many=True)
        return Response(serializer.data)

    @extend_schema(responses=AlbumSerializer)
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

        serializer = AlbumSerializerID(obj, exclude_fields=['main_artist__songs', 'main_artist__albums'])
        return Response(serializer.data, status=status.HTTP_200_OK)

    @extend_schema(responses=AlbumSerializer)
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

    @extend_schema(responses=AlbumSerializer)
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

    @extend_schema(responses=AlbumSerializer)
    def delete(self, request, id):
        try:
            obj = Album.objects.get(id=id)

        except Album.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)


class SongFilterView(APIView):
    @extend_schema(responses=SongSerializer)
    def get(self, request, year):
        songs = Song.objects.filter(year_of_release__gte=year)
        serializer = SongSerializer(songs, many=True, exclude_fields=['artists'])
        return Response(serializer.data, status=status.HTTP_200_OK)


class PerformsOnList(APIView):
    @extend_schema(responses=PerformsOnSerializer)
    def get(self, request):
        performances = PerformsOn.objects.all()
        serializer = PerformsOnSerializer(performances, many=True)
        return Response(serializer.data)

    @extend_schema(responses=PerformsOnSerializer)
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

        serializer = PerformsOnSerializerID(obj, exclude_fields=['song__artists', 'artist__songs', 'artist__albums'])
        return Response(serializer.data, status=status.HTTP_200_OK)

    @extend_schema(responses=PerformsOnSerializer)
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

    @extend_schema(responses=PerformsOnSerializer)
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

    @extend_schema(responses=PerformsOnSerializer)
    def delete(self, request, id):
        try:
            obj = PerformsOn.objects.get(id=id)

        except PerformsOn.DoesNotExist:
            msg = {"msg": "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)


class ArtistViewsStatistics(APIView):
    @extend_schema(responses=ArtistViewsStatisticsSerializer)
    def get(self, request):
        # artists ordered by the average of their performances' views
        statistics = Artist.objects.annotate(
            avg_views=Avg('performson__nr_of_views')
        ).order_by('-avg_views')

        serializer = ArtistViewsStatisticsSerializer(statistics, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)


class SongsNumberStatistics(APIView):
    @extend_schema(responses=SongsNumberStatisticsSerializer)
    def get(self, request):
        # songs ordered by the number of performances
        statistics = Song.objects.annotate(
            nr_performances=Count('performson__song_id')
        ).order_by('-nr_performances')

        serializer = SongsNumberStatisticsSerializer(statistics, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)