package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.beans.property.SimpleStringProperty;

public class MemberFileReader {
	Scanner sc = null;
	
	public MemberFileReader(File f) throws FileNotFoundException {
		sc = new Scanner(f);
	}
	public ArrayList<Member> readMember() {
		ArrayList<Member> retObj = new ArrayList<Member>();
		while(sc.hasNext()) {
			Member m = new Member();
			String strArr[] = sc.nextLine().split("\t");
			//fieldCount : 입력 받고자 했던 필드 수, strArr.Length : 실제 입력된 필드 수
			int fieldCount=4;
			String newStrArr[] = new String[fieldCount];
			if(fieldCount != strArr.length) {
				for(int i = 0; i < strArr.length;i++)
					newStrArr[i] = strArr[i];
				for(int i= strArr.length; i < fieldCount;i++)
					newStrArr[i] = "";
			}
			// 파일에 저장된 정보를 줄단위로 읽고, 각 줄을 탭 기호로 스캐닝하여 토큰들을 배열 객체에 저장
			m.setUid(strArr[0]);
			m.setUpw(strArr[1]);
			m.setUname(strArr[2]);
			m.setContact(strArr[3]);
			retObj.add(m);			
		}
		return retObj;
	}
}