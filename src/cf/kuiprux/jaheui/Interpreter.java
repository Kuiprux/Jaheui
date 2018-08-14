package cf.kuiprux.jaheui;

import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
	
	char[][] codeCharacters;
	int maxX = 0, maxY = 0;
	int pointer = 0;
	Scanner sc = new Scanner(System.in);
	ArrayList<AheuiStorage> storage = new ArrayList<AheuiStorage>(HangeulUtil.JONGSEONGS.length);
	public static final int[] JONGSEONG_COUNT = {0, 2, 4, 4, 2, 5, 5, 3, 5, 7, 9, 9, 7, 9, 9, 8, 4, 4, 6, 2, 4, 0, 3, 4, 3, 4, 4, 0};
	int xPos = 0;
	int yPos = 0;
	int xStep = 0;
	int yStep = 1;
	
	public Interpreter() {
		for(int i = 0; i < HangeulUtil.JONGSEONGS.length; i++) {
			int mode = AheuiStorage.MODE_STACK;
			if(i == 'ㅇ'-'ㄱ') mode = AheuiStorage.MODE_QUEUE;
			if(i == 'ㅎ'-'ㄱ') mode = AheuiStorage.MODE_TURNEL;
			storage.add(new AheuiStorage(mode));
		}
	}

	public void interprete(String code) {
		String[] codeLines = code.split("\n");
		codeCharacters = new char[codeLines.length][];
		maxY = codeCharacters.length;
		for(int i = 0; i < codeLines.length; i++) {
			if(codeLines[i].length() > maxX) maxX = codeLines[i].length();
		}
		for(int i = 0; i < codeCharacters.length; i++) {
			codeCharacters[i] = new char[maxX];
			char[] charArray = codeLines[i].toCharArray();
			for(int j = 0; j < codeLines[i].length(); j++) {
				codeCharacters[i][j] = charArray[j];
			}
		}
		while(true) {
			if(HangeulUtil.isEumjeol(codeCharacters[yPos][xPos])) {
				char[] eumuns = HangeulUtil.getEumuns(codeCharacters[yPos][xPos]);
				processMoeum(eumuns[1]);
				processJaeum(eumuns[0], eumuns[2]);
			}
			xPos += xStep;
			yPos += yStep;
			if(xPos < 0) xPos = maxX + xPos;
			if(yPos < 0) yPos = maxY + yPos;
			if(xPos >= maxX) xPos = xPos - maxX;
			if(yPos >= maxY) yPos = yPos - maxY;
		}
	}
	
	private void processJaeum(char choseong, char jongseong) {
		AheuiStorage curStorage = storage.get(pointer);
		char pointerChar = HangeulUtil.JONGSEONGS[pointer];
		switch(choseong) {
		//Group ㅇ
		case 'ㅇ':
			break;
		case 'ㅎ':
			int[] exitNum = curStorage.pop(1);
			System.exit(exitNum == null ? 0 : exitNum[0]);
			break;
		//group ㄷ
		case 'ㄷ':
			int[] addOperands = curStorage.pop(2);
			if(addOperands != null) {
				curStorage.push(addOperands[0] + addOperands[1]);
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		case 'ㄸ':
			int[] multiplyOperands = curStorage.pop(2);
			if(multiplyOperands != null) {
				curStorage.push(multiplyOperands[0] * multiplyOperands[1]);
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		case 'ㅌ':
			int[] substractOperands = curStorage.pop(2);
			if(substractOperands != null) {
				curStorage.push(substractOperands[1] - substractOperands[0]);
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		case 'ㄴ':
			int[] divideOperands = curStorage.pop(2);
			if(divideOperands != null) {
				curStorage.push(divideOperands[1] / divideOperands[0]);
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		case 'ㄹ':
			int[] modulusOperands = curStorage.pop(2);
			if(modulusOperands != null) {
				curStorage.push(modulusOperands[1] % modulusOperands[0]);
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		//Group ㅁ
		case 'ㅁ':
			int[] popOperand = curStorage.pop(1);
			if(popOperand != null) {
				if(jongseong == 'ㅇ') {
					System.out.print(popOperand[0]);
				} else if(jongseong == 'ㅎ') {
					System.out.print((char) popOperand[0]);
				}
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		case 'ㅂ':
			if(jongseong == 'ㅇ') {
				String str = sc.nextLine();
				curStorage.push(Util.isInteger(str) ? Integer.parseInt(str) : -1);
			}
			if(jongseong == 'ㅎ') {
				String str = sc.nextLine();
				curStorage.push((int) str.charAt(0));
			}
			else
				curStorage.push(JONGSEONG_COUNT[HangeulUtil.indexChar(HangeulUtil.JONGSEONGS, jongseong)]);
			break;
		case 'ㅃ':
			int[] doubleOperand = curStorage.pop(1);
			if(doubleOperand != null) {
				curStorage.push(doubleOperand[0]);
				curStorage.push(doubleOperand[0]);
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		case 'ㅍ':
			int[] swapOperands = curStorage.pop(2);
			if(swapOperands != null) {
				if(pointerChar == 'ㅇ') {
					curStorage.pushLast(swapOperands[0]);
					curStorage.pushLast(swapOperands[1]);
				} else {
					curStorage.push(swapOperands[0]);
					curStorage.push(swapOperands[1]);
				}
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		//Group ㅅ
		case 'ㅅ':
			pointer = HangeulUtil.indexChar(HangeulUtil.JONGSEONGS, jongseong);
			break;
		case 'ㅆ':
			int[] moveOperand = curStorage.pop(1);
			if(moveOperand != null) {
				storage.get(HangeulUtil.indexChar(HangeulUtil.JONGSEONGS, jongseong)).push(moveOperand[0]);
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		case 'ㅈ':
			int[] compareOperands = curStorage.pop(2);
			if(compareOperands != null) {
				curStorage.push(compareOperands[0] <= compareOperands[1] ? 1 : 0);
			} else {
				xStep = -xStep;
				yStep = -yStep;
			}
			break;
		case 'ㅊ':
			int[] checkOperand = curStorage.pop(1);
			if(checkOperand == null || checkOperand[0] == 0) {
				xStep = -xStep;
				yStep = -yStep;
			}
		}
	}
	
	private void processMoeum(char jungseong) {
		switch(jungseong) {
		case 'ㅏ':
			xStep = 1;
			yStep = 0;
			break;
		case 'ㅓ':
			xStep = -1;
			yStep = 0;
			break;
		case 'ㅗ':
			xStep = 0;
			yStep = -1;
			break;
		case 'ㅜ':
			xStep = 0;
			yStep = 1;
			break;
		case 'ㅑ':
			xStep = 2;
			yStep = 0;
			break;
		case 'ㅕ':
			xStep = -2;
			yStep = 0;
			break;
		case 'ㅛ':
			xStep = 0;
			yStep = -2;
			break;
		case 'ㅠ':
			xStep = 0;
			yStep = 2;
			break;
		case '-':
			yStep = -yStep;
			break;
		case 'ㅣ':
			xStep = -xStep;
			break;
		case 'ㅢ':
			xStep = -xStep;
			yStep = -yStep;
			break;
		}
	}
}
