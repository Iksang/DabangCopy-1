package kr.co.tjeit.dabangcopy.util;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjeit.dabangcopy.data.Realtor;
import kr.co.tjeit.dabangcopy.data.Room;
import kr.co.tjeit.dabangcopy.data.Subway;
import kr.co.tjeit.dabangcopy.data.University;
import kr.co.tjeit.dabangcopy.data.User;

/**
 * Created by tjoeun on 2017-08-22.
 */

public class GlobalData {

    public static User loginUser = null;

    public static List<User> users = new ArrayList<>();
    public static List<Realtor> relators = new ArrayList<>();
    public static List<Room> allRooms = new ArrayList<>();

    //    지하철 역 목록
    public static List<Subway> stations = new ArrayList<>();

    //    지하철 역 목록
    public static List<University> universities = new ArrayList<>();

    // 더미데이터 쌓기.
    public static void initGlobalData() {
//            1.사용자 목록
        users.clear();
        users.add(new User(1, "AAA", "Facebook", "URL", "111-1111-111"));
        users.add(new User(2, "BBB", "Dabang", "URL", "222-2222-2222"));
        users.add(new User(3, "CCC", "Kakao", "URL", "333-3333-3333"));
        users.add(new User(4, "DDD", "Facebook", "URL", "444-4444-4444"));
        users.add(new User(5, "EEE", "Dabang", "URL", "555-5555-6666"));
        users.add(new User(6, "FFF", "Kakao", "URL", "666-6666-6666"));
        users.add(new User(7, "GGG", "Facebook", "URL", "777-7777-7777"));
        users.add(new User(8, "HHH", "Facebook", "URL", "888-8888-8888"));
        users.add(new User(9, "III", "Dabang", "URL", "999-9999-9999"));
//        2. 부동산 목록

        relators.add(new Realtor(1, "A부동산", "서울", "A대표", "456-7891"));
        relators.add(new Realtor(2, "B부동산", "인천", "B대표", "326-7891"));
        relators.add(new Realtor(3, "C부동산", "남양주", "C대표", "876-7891"));
        relators.add(new Realtor(4, "D부동산", "용인", "D대표", "999-7891"));
        relators.add(new Realtor(5, "E부동산", "분당", "E대표", "564-7891"));

//        3. 방 목록
        allRooms.add(new Room(1, 500, 45, 1, 12.4, -1, 0, 37.541442, 126.994628, "1번방에 대한 설명","서울시 은평구 불광동", relators.get(0)));
        allRooms.get(0).getPhotoURLs().add("https://d1774jszgerdmk.cloudfront.net/1024/77065296-0eac-4080-afda-fe725f8e6bf9");
        allRooms.get(0).getPhotoURLs().add("https://d1774jszgerdmk.cloudfront.net/1024/424f83db-9cac-4d1b-819f-5bf555a2eaa5");
        allRooms.get(0).getPhotoURLs().add("https://d1774jszgerdmk.cloudfront.net/1024/194ea6c4-583f-4e6e-a78d-6084a3ed2ca0");
        allRooms.get(0).getPhotoURLs().add("https://d1774jszgerdmk.cloudfront.net/1024/9e89a378-ab08-4ae8-ac56-4097132e6231");


        allRooms.add(new Room(2, 500, 35, 1, 10.3, 2, 5, 37.564015, 126.970001, "2번방에 대한 설명","서울시 마포구 창천동", relators.get(1)));
        allRooms.add(new Room(3, 1000, 25, 2, 27.3, 3, 10, 37.571033, 127.02579, "3번방에 대한 설명","서울시 마포구 창천동", relators.get(2)));
        allRooms.add(new Room(4, 1500, 25, 3, 45.7, 1, 8, 37.590235, 126.983899, "4번방에 대한 설명","서울시 마포구 창천동", relators.get(3)));
        allRooms.add(new Room(5, 11500, 0, 2, 27.5, -1, 9, 37.587098, 127.027386, "5번방에 대한 설명","서울시 관악구 신림동", relators.get(4)));
        allRooms.add(new Room(6, 12500, 0, 3, 40.8, 0, 5, 37.590008, 127.102333, "6번방에 대한 설명","서울시 관악구 신림동", relators.get(2)));
        allRooms.add(new Room(7, 300, 55, 2, 25.8, 2, 8, 37.626925, 127.041106, "7번방에 대한 설명","서울시 관악구 신림동", relators.get(3)));
        allRooms.add(new Room(8, 6500, 0, 1, 12.1, 1, 6, 37.604353, 126.917125, "8번방에 대한 설명","서울시 관악구 신림동", relators.get(4)));


//        4.지하철 역 목록
//        지하철역 자체를 추가
        stations.add(new Subway("종로3가"));
//        추가된 지하철 역의 호선수 추가
        stations.get(0).getLines().add("1호선");
        stations.get(0).getLines().add("3호선");
        stations.get(0).getLines().add("5호선");

        stations.add(new Subway("왕십리"));
        stations.get(1).getLines().add("2호선");
        stations.get(1).getLines().add("5호선");
        stations.get(1).getLines().add("분당선");
        stations.get(1).getLines().add("경의중앙선");

        stations.add(new Subway("신림"));
        stations.get(2).getLines().add("2호선");

        stations.add(new Subway("교대"));
        stations.get(3).getLines().add("2호선");
        stations.get(3).getLines().add("3호선");


        stations.add(new Subway("신도림"));
        stations.get(4).getLines().add("2호선");

        stations.add(new Subway("서울대입구"));
        stations.get(5).getLines().add("2호선");

        stations.add(new Subway("낙성대"));
        stations.get(6).getLines().add("2호선");

        stations.add(new Subway("방배"));
        stations.get(7).getLines().add("2호선");

        stations.add(new Subway("서초"));
        stations.get(8).getLines().add("2호선");

//        5. 대학교 목록 추가
        universities.add(new University("서울대학교"));
        universities.add(new University("연세대학교"));
        universities.add(new University("고려대학교"));
        universities.add(new University("서강대학교"));
        universities.add(new University("성균관대학교"));
        universities.add(new University("한양대학교"));


//         ※ 관계들을 설정

//        1번방에서 가까운 역은 종로3가다
        allRooms.get(0).getNearStations().add(stations.get(0));
//        2번방에서 가까운 역은 신림이다.
        allRooms.get(1).getNearStations().add(stations.get(2));
//        2번방에서 교대역도 가깝다.
        allRooms.get(1).getNearStations().add(stations.get(3));

//        3번방 : 왕십리
        allRooms.get(2).getNearStations().add(stations.get(1));

//        6번방 : 교대
        allRooms.get(5).getNearStations().add(stations.get(3));

//        0번방 : 서울대
        allRooms.get(0).getNearUniversities().add(universities.get(0));
//        1번방 : 연세, 고려
        allRooms.get(1).getNearUniversities().add(universities.get(1));
        allRooms.get(1).getNearUniversities().add(universities.get(2));
//        2번방 : 서강, 한양, 성균관, 서울
        allRooms.get(2).getNearUniversities().add(universities.get(3));
        allRooms.get(2).getNearUniversities().add(universities.get(4));
        allRooms.get(2).getNearUniversities().add(universities.get(5));
        allRooms.get(2).getNearUniversities().add(universities.get(0));


    }
}
