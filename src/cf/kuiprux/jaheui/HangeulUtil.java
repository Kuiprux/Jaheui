package cf.kuiprux.jaheui;

public class HangeulUtil {
	public static final char[] CHOSEONGS = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
	public static final char[] JUNGSEONGS = { 'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'};
	public static final char[] JONGSEONGS = {' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
	
	public static final char HANGEUL_START = '가';
	public static final char HANGEUL_END = '힣';
	
	public static char getEumjeol(char choseong, char jungseong) {
		return getEumjeol(choseong, jungseong, ' ');
	}
	
	public static char getEumjeol(char choseong, char jungseong, char jongseong) {
		int choseongIndex = indexChar(CHOSEONGS, choseong);
		int jungseongIndex = indexChar(JUNGSEONGS, jungseong);
		int jongseongIndex = indexChar(JONGSEONGS, jongseong);
		
		if(choseongIndex == -1) throw new HangulException("Given character is not choseong");
		if(jungseongIndex == -1) throw new HangulException("Given character is not jungseong");
		if(jongseongIndex == -1) throw new HangulException("Given character is not jongseong");
		
		return (char) (HANGEUL_START +
				(choseongIndex*JUNGSEONGS.length*JONGSEONGS.length) +
				(jungseongIndex*JONGSEONGS.length) +
				(jongseongIndex));
	}
	
	public static char[] getEumuns(char eumjeol) {
		if(!isEumjeol(eumjeol)) throw new HangulException("Given character is not eumjeol");
		
		char[] eumuns = new char[3];
		int num = eumjeol-HANGEUL_START;
		int jongseong = num%JONGSEONGS.length;
		num = (num-jongseong)/JONGSEONGS.length;
		int jungseong = num%JUNGSEONGS.length;
		num = (num-jungseong)/JUNGSEONGS.length;
		int choseong = num % JUNGSEONGS.length;

		eumuns[2] = JONGSEONGS[jongseong];
		eumuns[1] = JUNGSEONGS[jungseong];
		eumuns[0] = CHOSEONGS[choseong];
		return eumuns;
	}
	
	public static boolean isChoseong(char choseong) {
		return indexChar(CHOSEONGS, choseong) != -1;
	}

	public static boolean isJongseong(char jungseong) {
		return indexChar(JUNGSEONGS, jungseong) != -1;
	}

	public static boolean isJungseong(char jongseong) {
		return indexChar(JONGSEONGS, jongseong) != -1;
	}
	
	public static boolean isEumjeol(char character) {
		return HANGEUL_START <= character && character <= HANGEUL_END;
	}
	
	public static int indexChar(char[] chars, char aChar) {
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == aChar) return i;
		}
		return -1;
	}
}
