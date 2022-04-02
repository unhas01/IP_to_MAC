import java.util.Scanner;
import java.util.StringTokenizer;

public class CN_2 {
	
	public static String class_find(int n) {																		// IP�ּҿ��� ù��° ����Ʈ ������ class �Ǻ�
		if(n >= 0 && n <=127)																						// 0 ~ 127 : A
			return "A";
		else if(n >= 128 && n <= 191)																				// 128 ~ 191 : B
			return "B";
		else if(n >= 192 && n <= 223)																				// 192 ~ 223 : C
			return "C";
		else if(n >= 224 && n <= 239)																				// 224 ~ 239 : ��Ƽĳ��Ʈ(M���� ���)
			return "M";
		else																										// �� ���� ���(��ȿ���� �ʴ�) : F�� ���
			return "F";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
			
		for(int i = 0; i < 7; i++) {																				// 7���� ����
			System.out.println("[Example input and output #" + (i+1) + "]");
			System.out.println("\t//Find Ethernet multicast MAC address for a multicast IP address");
			System.out.print("\t> Input a multicast IP address: ");
			
			String IP_address = input.nextLine();																	// IP�ּ� �Է�
			StringTokenizer st = new StringTokenizer(IP_address, ".");												// "."�� �������� ��ū���� �ڸ�
			int arr [] = new int[st.countTokens()];																	// ��ū ������ŭ �迭 ���� (��ū���� : 4)
			
			for(int j = 0; j < arr.length; j++) {	
				int n = Integer.parseInt(st.nextToken());															// arr�� ���ڿ��� ���ڷ� ��ȯ�� ���� (10���� ��) 
				
				arr[j] = n;																							// arr[0] : 1��° byte
																													// arr[1] : 2��° byte
																													// arr[2] : 3��° byte
																													// arr[3] : 4��° byte
			}																												
			
			
			if((arr[0] < 0 || arr[0] >= 256) || (arr[1] < 0 || arr[1] >= 256) || (arr[2] < 0 || arr[2] >= 256) 		// if 0���� �۰ų� 256�̻��� ���� ���� ��� : �߸��� �ּ� 
					|| (arr[2] < 0 || arr[2] >= 256)) {																// break ��Ŵ!!!!!!!!!!
				System.out.println("\t> It is NOT a valid IP address.");
				break;
			}
			
			
			String class_result = class_find(arr[0]);																// class ��� �� ����
			
			if(class_result == "A" || class_result == "B" || class_result == "C") {									// class�� A,B,C�� ���(unicast)
				System.out.println("\t> It is a class " + class_result + " unicast address.");
				System.out.println();
			}
			else if(class_result == "M") {																			// multicast�� ���
				String MAC_address = new String("01:00:5E:");														// ���� MAC�ּ� ��� ���� ���� �ʱ�ȭ
				String colon = new String(":");																		// ":"
				String zero = new String("0");																		// "0"
				
				// arr[1], arr[2], arr[3]�� ���� ���ִ� 10���� ���� 16������ ��ȯ	
				int res = 0;
				if(arr[1] >= 128) {																					// 2��° byte���� 128���� ũ�� -> ù bit�� 1�� ����
					res = arr[1] - 128;																				// 128�� ���� -> ù bit 1 -> 0 
				}
				else {																								// 128���� ���� ��� -> �״��
					res = arr[1];
				}
				
				String fourth = Integer.toHexString(res).toUpperCase();												// MAC�ּҿ��� 4,5,6��° ����		
				if(fourth.length() == 1 && arr[1] < 16) {															// 16���� ��ȯ�� ������ ���̰� 1���� 0000�� ������ ����
					fourth = zero + fourth;																			// arr[]���� 16���� ������ 8-bit�� ���� 4-bit�� 0000 16���� ũ�� ���� 4 - bit�� 0000
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
				
				MAC_address = MAC_address.concat(fourth).concat(colon).concat(fifth).concat(colon).concat(sixth);	// MAC�ʱ�ȭ �� ������ �߰��ؼ� �ϼ�
				
				System.out.println("\t> It is a valid multicast address.");
				System.out.println("\t> Mapped multicast MAC address: " + MAC_address);
				System.out.println();
			}
			else if(class_result == "F") {																			// ��ȿ���� �ʴ� �ּ��� ���
				System.out.println("\t> It is NOT a valid IP address.");
				System.out.println();
			}
			
			
		}
		

		
		input.close();
	}

}


// �߰��� �κ�
// 16���� ��ȯ�� ��� �� 09�� 9�θ� ������ �� 
// 09�� �������� �����Ұ�!

