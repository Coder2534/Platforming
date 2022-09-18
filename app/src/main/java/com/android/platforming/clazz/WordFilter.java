package com.android.platforming.clazz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFilter {
    public static List<String> forbiddenWords = Arrays.asList(
            "느금마", "니애미", "니어미", "니엄마", "니애비", "느그애비", "느그애미", "애미터", "애미뒤", "앰뒤", "앰창", "갈보",
            "강간", "개같", "개년", "개뇬", "개미친", "개부랄", "개불알", "개빠구리", "개뼉다구", "개새", "개색", "개쌔끼", "개자석",
            "개자슥", "개자식", "지랄", "꼴통", "느그엄마", "느검마", "니기미", "니미", "대가리", "대갈", "대갈빡", "대갈통", "대굴빡",
            "뒈진다", "뒤질", "등쉰", "등신", "딸따뤼", "딸따리", "딸딸", "딸딸이", "똘추", "매춘", "몸파는", "발정", "배때지", "병신",
            "보지", "보짓", "보털", "부랄", "부럴", "불알", "븅딱", "븅삼", "븅쉰", "븅신", "빙딱", "빙삼", "빙시", "빙신", "빠구리",
            "빠구뤼", "빠꾸리", "빠꾸뤼", "빠순이", "빠큐", "뻑큐", "뽀르노", "뽀오지", "사까쉬", "사까시", "상노무", "상놈", "새1끼",
            "새갸", "새꺄", "새뀌", "새끼", "새뤼", "새리", "새캬", "새키", "성감대", "성경험", "성관계", "성교육", "섹하고", "섹하구",
            "섹하자", "섹하장", "쉬빨", "쉬뻘", "쉬뿔", "쉬파", "쉬팍", "쉬팔", "쉬팡", "쉬펄", "쉬퐁", "쉬풀", "스너프", "스댕", "스뎅",
            "스발", "스벌", "스와핑", "스왑", "스트립", "스팔", "스펄", "슴가", "싀발", "싀밸", "싀벌", "싀벨", "싀봉", "싀팍", "싀팔",
            "싀펄", "시1발", "싸갈통", "싸까시", "싸이코", "싸카시", "쌍너엄", "쌍넌", "쌍넘", "쌍녀언", "쌍년", "쌍놈", "쌍뇬", "쌍눔",
            "쌍늠", "썅넘", "썅년", "썅놈", "썅뇬", "썅눔", "썅늠", "써글", "썩을넘", "썩을년", "썩을놈", "썩을뇬", "썩을눔", "썩을늠",
            "씌바", "씌박", "씌발", "씌방", "씌밸", "씌벌", "씌벙", "씌벨", "씌부랄", "씌불", "씌블", "씌빌", "씌빨", "씌뻘", "씌파",
            "씌팍", "씌팔", "씌팡", "씌펄", "씌퐈", "씌퐝", "씨발", "씨1발", "씨팔", "씹", "아가리", "아가지", "아갈", "아괄", "아구리",
            "아구지", "아구창", "아굴창", "자쥐", "자즤", "조까", "조옷", "좆", "주둥아리", "주둥이", "창녀", "창년", "후레", "후장");

    public static boolean isForbiddenWords(String word) {
        Set<String> filteredBlackWords = getFiteredForbiddenWords(word);
        return (filteredBlackWords.size() > 0) ? true : false;
    }

    public static Set<String> getFiteredForbiddenWords(String word) {
        if(word == null) {
            return new HashSet<>();
        }

        String blackWordsRegEx = "";
        for(String bWord : forbiddenWords) {
            blackWordsRegEx +=  bWord + "|";
        }

        if(blackWordsRegEx.length() > 0) {
            blackWordsRegEx = blackWordsRegEx.substring(0, blackWordsRegEx.length() - 1);
        }

        Set<String> fiteredForbiddenWords = new HashSet<>();
        Pattern p = Pattern.compile(blackWordsRegEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(word);
        while(m.find()) {
            fiteredForbiddenWords.add(m.group());
        }

        return fiteredForbiddenWords;
    }
}