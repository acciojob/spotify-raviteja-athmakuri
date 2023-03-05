package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;
   // private Object exception;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User newUser=new User(name,mobile);
        users.add(newUser);
        return newUser;
    }

    public Artist createArtist(String name) {
        Artist newArtist=new Artist(name);
        artists.add(newArtist);
        artistAlbumMap.put(newArtist,new ArrayList<Album>());
        return newArtist;
    }

    public Album createAlbum(String title, String artistName) {


            Album newAlbum=new Album(title);
            albums.add(newAlbum);



        List<Album> al=new ArrayList<>();
        al.add(newAlbum);
        if(!artists.contains(artistName)){
            Artist a=new Artist(artistName);
            artists.add(a);
            artistAlbumMap.put(a,al);
        }else artistAlbumMap.get(artistName).add(newAlbum);
        albums.add(newAlbum);

//        for(Artist artist:artistAlbumMap.keySet()){
//            if()
//        }
        return newAlbum;
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        Song s=new Song(title,length);
        songs.add(s);
        List<Song> al=new ArrayList<>();
        al.add(s);
        int flag=0;
        for(int i=0;i<albums.size();i++){
            if(albums.get(i).getTitle()==albumName){
                albumSongMap.put(albums.get(i),al);
                flag++;
            }
        }
          if(flag==0){
              Album a=new Album(albumName);
              albumSongMap.put(a,al);

             throw new Exception("Album does not exist");}



        return s;

    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        Playlist p=new Playlist(title);
        ArrayList<Song> s=new ArrayList<>();
        ArrayList<User> list=new ArrayList<>();
        for(int i=0;i<songs.size();i++){
            if(songs.get(i).getLength()==length)
                s.add(songs.get(i));
        }
        playlistSongMap.put(p,s);
        int flag=0;
        for(int i=0;i<users.size();i++){
            if(users.get(i).getMobile()==mobile) {
                flag++;
                list.add(users.get(i));
                break;
            }
        }

        if(flag==0){


            throw new Exception("User does not exist");}
        else playlistListenerMap.put(p,list);
        return p;
    }
     //HashMap<Playlist,List<Song>> hm=new HashMap<>();
    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        Playlist p=new Playlist(title);
        ArrayList<Song> list=new ArrayList<>();
        for(int i=0;i<songs.size();i++){
            if(songTitles.contains(songs.get(i).getTitle()))
             list.add(songs.get(i));
        }
        playlistSongMap.put(p,list);
        ArrayList<User> l=new ArrayList<>();
        int flag=0;
        for(int i=0;i<users.size();i++){
            if(users.get(i).getMobile()==mobile) {
                flag++;
                l.add(users.get(i));
                break;
            }
        }

        if(flag==0)
            throw new Exception("User does not exist");
        else playlistListenerMap.put(p,l);
        return p;

    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        //Playlist p;
        User u=new User();
        int flag=0;
        for(int i=0;i<users.size();i++){
            if(users.get(i).getMobile()==mobile) {
                u = users.get(i);
                flag++;
            }
        }
        ArrayList<User> list=new ArrayList<User>();
        list.add(u);
        if(flag==0)
            throw new Exception("User does not exist");
        int j=0;
        for(Playlist play:playlistListenerMap.keySet()){
            if(play.getTitle()==(playlistTitle)){
                if(flag>0)
                playlistListenerMap.get(play).add(u);
                j++;
            }
        }
        if(j==0){
            playlistListenerMap.put(new Playlist(playlistTitle),list);
            throw new Exception("Playlist does not exist");
        }

         return new Playlist(playlistTitle);
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {

        User curUser= new User();
        boolean flag2= false;
        for(User user: users){
            if(user.getMobile().equals(mobile)){
                curUser=user;
                flag2= true;
                break;
            }
        }
        if (flag2==false){
            throw new Exception("User does not exist");
        }

        Song song = new Song();
        boolean flag = false;
        for(Song cursong : songs){
            if(cursong.getTitle().equals(songTitle)){
                song=cursong;
                flag=true;
                break;
            }
        }
        if (flag==false){
            throw new Exception("Song does not exist");
        }

        //public HashMap<Song, List<User>> songLikeMap;
        List<User> users = new ArrayList<>();
        if(songLikeMap.containsKey(song)){
            users=songLikeMap.get(song);
        }
        if (!users.contains(curUser)){
            users.add(curUser);
            songLikeMap.put(song,users);
            song.setLikes(song.getLikes()+1);


//            public HashMap<Album, List<Song>> albumSongMap;
            Album album = new Album();
            for(Album curAlbum : albumSongMap.keySet()){
                List<Song> temp = albumSongMap.get(curAlbum);
                if(temp.contains(song)){
                    album=curAlbum;
                    break;
                }
            }


//            public HashMap<Artist, List<Album>> artistAlbumMap;
            Artist artist = new Artist();
            for(Artist curArtist : artistAlbumMap.keySet()){
                List<Album> temps = artistAlbumMap.get(curArtist);
                if(temps.contains(album)){
                    artist=curArtist;
                    break;
                }
            }

            artist.setLikes(artist.getLikes()+1);
        }
        return song;

    }

    public String mostPopularArtist() {
        int max=0;
        String str="";
        for(int i=0;i<artists.size();i++){
            if(artists.get(i).getLikes()>max){
                max=artists.get(i).getLikes();
                str=artists.get(i).getName();
            }
            //max=Math.max(artists.get(i).getLikes(),max);

        }
        return str;
    }

    public String mostPopularSong() {
        int max=0;
        String str="";
        for(int i=0;i<songs.size();i++){
            if(songs.get(i).getLikes()>max){
                max=songs.get(i).getLikes();
                str=songs.get(i).getTitle();
            }
        }
        return str;
    }
}
//for(Playlist play:playlistSongMap.keySet()){
//        if(play.getTitle()==(playlistTitle)){
//        if(flag>0){
//        if(!playlistListenerMap.containsKey(playlistTitle))
//        playlistListenerMap.put(new Playlist(playlistTitle),list);
//        }
//
//        }else{
//        Playlist play=new Playlist(playlistTitle);
//        playlistListenerMap.put(play,list);
//        throw new Exception("Playlist does not exist");
//        }