from django.test import TestCase
from rest_framework.test import APIClient
from rest_framework import status
from Music.models import *


class FilterSongsByYearTest(TestCase):
    def setUp(self):
        self.client = APIClient()

        Song.objects.create(song_name="test1", composer="test1", genre="test1", year_of_release=2007)
        Song.objects.create(song_name="test2", composer="test2", genre="test2", year_of_release=2012)
        Song.objects.create(song_name="test3", composer="test3", genre="test3", year_of_release=2020)

    def test_filter_songs_by_year(self):
        url = '/music/filter-songs-by-year/2017/'
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data, [
            {
                "id": 3,
                "song_name": "test3",
                "composer": "test3",
                "genre": "test3",
                "year_of_release": 2020
            }
        ])


class SongsNumberStatisticsTest(TestCase):
    def setUp(self):
        self.client = APIClient()

        Song.objects.create(song_name="test", composer="test1", genre="test1", year_of_release=2007)
        Song.objects.create(song_name="test2", composer="test2", genre="test2", year_of_release=2012)
        Song.objects.create(song_name="test3", composer="test3", genre="test3", year_of_release=2020)

        Artist.objects.create(artist_name="test1", real_name="test1", country="test1", email="test1")
        Artist.objects.create(artist_name="test2", real_name="test2", country="test2", email="test2")
        Artist.objects.create(artist_name="test3", real_name="test3", country="test3", email="test3")

        PerformsOn.objects.create(song_id=Song.objects.get(id=1), artist_id=Artist.objects.get(id=1), nr_of_views=100, duration="")
        PerformsOn.objects.create(song_id=Song.objects.get(id=1), artist_id=Artist.objects.get(id=3), nr_of_views=200, duration="")
        PerformsOn.objects.create(song_id=Song.objects.get(id=2), artist_id=Artist.objects.get(id=3), nr_of_views=200, duration="")
        PerformsOn.objects.create(song_id=Song.objects.get(id=1), artist_id=Artist.objects.get(id=2), nr_of_views=200, duration="")
        PerformsOn.objects.create(song_id=Song.objects.get(id=3), artist_id=Artist.objects.get(id=2), nr_of_views=300, duration="")
        PerformsOn.objects.create(song_id=Song.objects.get(id=3), artist_id=Artist.objects.get(id=3), nr_of_views=200, duration="")

    def test_songs_number_statistics(self):
        url = '/music/songs/order-by-performances/'
        response = self.client.get(url)
        self.maxDiff = None
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data, [
            {
                "song_name": "test",
                "nr_performances": 3
            },
            {
                "song_name": "test3",
                "nr_performances": 2
            },
            {
                "song_name": "test2",
                "nr_performances": 1
            }
        ])



