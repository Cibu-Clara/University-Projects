from django.http import Http404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from Song.models import Song
from Song.serializer import SongSerializer


class SongList(APIView):
    def get(self, request, format=None):
        songs = Song.objects.all()
        serializer = SongSerializer(songs, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
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
            msg = {"msg" : "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        serializer = SongSerializer(obj)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def put(self, request, id):
        try:
            obj = Song.objects.get(id=id)

        except Song.DoesNotExist:
            msg = {"msg" : "not found error"}
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
            msg = {"msg" : "not found error"}
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
            msg = {"msg" : "not found"}
            return Response(msg, status=status.HTTP_404_NOT_FOUND)

        obj.delete()
        return Response({"msg": "deleted"}, status=status.HTTP_204_NO_CONTENT)