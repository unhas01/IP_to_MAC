import java.util.Scanner;
import java.util.StringTokenizer;

public class CN_2 {
	
	public static String class_find(int n) {																		// IP주소에서 첫번째 바이트 값으로 class 판별
		if(n >= 0 && n <=127)																						// 0 ~ 127 : A
			return "A";
		else if(n >= 128 && n <= 191)																				// 128 ~ 191 : B
			return "B";
		else if(n >= 192 && n <= 223)																				// 192 ~ 223 : C
			return "C";
		else if(n >= 224 && n <= 239)																				// 224 ~ 239 : 멀티캐스트(M으로 출력)
			return "M";
		else																										// 그 외의 경우(유효하지 않는) : F로 출력
			return "F";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
			
		for(int i = 0; i < 7; i++) {																				// 7가지 예시
			System.out.println("[Example input and output #" + (i+1) + "]");
			System.out.println("\t//Find Ethernet multicast MAC address for a multicast IP address");
			System.out.print("\t> Input a multicast IP address: ");
			
			String IP_address = input.nextLine();																	// IP주소 입력
			StringTokenizer st = new StringTokenizer(IP_address, ".");												// "."을 기준으로 토큰으로 자름
			int arr [] = new int[st.countTokens()];																	// 토큰 개수만큼 배열 생성 (토큰개수 : 4)
			
			for(int j = 0; j < arr.length; j++) {	
				int n = Integer.parseInt(st.nextToken());															// arr에 문자열을 숫자로 변환후 저장 (10진수 값) 
				
				arr[j] = n;																							// arr[0] : 1번째 byte
																													// arr[1] : 2번째 byte
																													// arr[2] : 3번째 byte
																													// arr[3] : 4번째 byte
			}																												
			
			
			if((arr[0] < 0 || arr[0] >= 256) || (arr[1] < 0 || arr[1] >= 256) || (arr[2] < 0 || arr[2] >= 256) 		// if 0보다 작거나 256이상의 값이 들어온 경우 : 잘못된 주소 
					|| (arr[2] < 0 || arr[2] >= 256)) {																// break 시킴!!!!!!!!!!
				System.out.println("\t> It is NOT a valid IP address.");
				break;
			}
			
			
			String class_result = class_find(arr[0]);																// class 결과 값 변수
			
			if(class_result == "A" || class_result == "B" || class_result == "C") {									// class가 A,B,C인 경우(unicast)
				System.out.println("\t> It is a class " + class_result + " unicast address.");
				System.out.println();
			}
			else if(class_result == "M") {																			// multicast인 경우
				String MAC_address = new String("01:00:5E:");														// 최종 MAC주소 결과 저장 변수 초기화
				String colon = new String(":");																		// ":"
				String zero = new String("0");																		// "0"
				
				// arr[1], arr[2], arr[3]에 저장 되있는 10진수 값을 16진수로 변환	
				int res = 0;
				if(arr[1] >= 128) {																					// 2번째 byte값이 128보다 크다 -> 첫 bit가 1로 시작
					res = arr[1] - 128;																				// 128을 빼줌 -> 첫 bit 1 -> 0 
				}
				else {																								// 128보다 작은 경우 -> 그대로
					res = arr[1];
				}
				
				String fourth = Integer.toHexString(res).toUpperCase();												// MAC주소에서 4,5,6번째 값들		
				if(fourth.length() == 1 && arr[1] < 16) {															// 16진수 변환후 문자의 길이가 1개면 0000이 생략된 상태
					fourth = zero + fourth;																			// arr[]값이 16보다 작으면 8-bit중 상위 4-bit이 0000 16보다 크면 하위 4 - bit이 0000
				}
				else if(fourth.length() == 1 && arr[2] > 15) {
					fourth = fourth + zero;
				}
				
				String fifth = Integer.toHexString(arr[2]).toUpperCase();
				if(fifth.length() == 1 && arr[2] < 16) {
					fifth = zero + fifth;
				}
				else if(fifth.length() == 1 && arr[2] > 15) {
					fifth = fifth + zero;
				}
				
				String sixth = Integer.toHexString(arr[3]).toUpperCase();
				if(sixth.length() == 1 && arr[3] < 16) {
					sixth = zero + sixth;
				}
				else if(sixth.length() == 1 && arr[3] > 15) {
					sixth = sixth + zero;
				}
				
				MAC_address = MAC_address.concat(fourth).concat(colon).concat(fifth).concat(colon).concat(sixth);	// MAC초기화 한 변수에 추가해서 완성
				
				System.out.println("\t> It is a valid multicast address.");
				System.out.println("\t> Mapped multicast MAC address: " + MAC_address);
				System.out.println();
			}
			else if(class_result == "F") {																			// 유효하지 않는 주소인 경우
				System.out.println("\t> It is NOT a valid IP address.");
				System.out.println();
			}
			
			
		}
		

		
		input.close();
	}

}


// 추가할 부분
// 16진수 변환후 출력 시 09가 9로만 나오는 중 
// 09가 나오도록 변경할것!

