/**
 * 
 */
package bigdata.portal.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.ucsit.core.CsUtil;

/**
 * @author hyunseongkil
 * @see https://www.code.go.kr/stdcode/regCodeL.do
 */
@Mapper
public class AdresMapper {
	public List<Map<String,String>> getAllSidos(){
		String[] codes = "36,50,48,47,46,45,44,43,42,41,31,30,29,28,27,26,11".split(",");
		String[] names = "세종특별자치시,제주특별자치도,경상남도,경상북도,전라남도,전라북도,충청남도,충청북도,강원도,경기도,울산광역시,대전광역시,광주광역시,인천광역시,대구광역시,부산광역시,서울특별시".split(",");
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		for(int i=0; i<codes.length; i++) {
			Map<String,String> d = new HashMap<String, String>();
			list.add(d);
			d.put("sidoCode", codes[i]);
			d.put("sidoName", names[i]);
		}
		//
		return list;
		
	}
	
	public List<Map<String,String>> getAllSigungus(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.addAll(get36s());
		list.addAll(get50s());
		list.addAll(get48s());
		list.addAll(get47s());
		list.addAll(get46s());
		list.addAll(get45s());
		list.addAll(get44s());
		list.addAll(get43s());
		list.addAll(get42s());
		list.addAll(get41s());
		list.addAll(get31s());
		list.addAll(get30s());
		list.addAll(get29s());
		list.addAll(get28s());
		list.addAll(get27s());
		list.addAll(get26s());
		list.addAll(get11s());
		
		//
		return list;
	}
	
	
	/**
	 * 시군구 목록
	 * @param sidoCode 시도 코드
	 * @return
	 */
	public List<Map<String,String>> getSigungusBySidoCode(String sidoCode){
		List<Map<String,String>> alllist = getAllSigungus();
		//
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		for(Map<String,String> d : alllist) {
			if(sidoCode.equals(d.get("sidoCode"))) {
				list.add(d);
			}
		}
		
		//
		return list;
	}
	
	/**
	 * 세종특별시
	 * @return
	 */
	private  List<Map<String,String>> get36s(){
		String sidoCode = "36";
		String[] names = "세종특별자치시,".split(",");
		String[] codes = "110,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 제주
	 * @return
	 */
	private  List<Map<String,String>> get50s(){
		String sidoCode = "50";
		String[] names = "제주시,서귀포시,".split(",");
		String[] codes = "110,130,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 경남
	 * @return
	 */
	private  List<Map<String,String>> get48s(){
		String sidoCode = "48";
		String[] names = "창원시 의창구,창원시 성산구,창원시 마산합포구,창원시 마산회원구,창원시 진해구,진주시,통영시,사천시,김해시,밀양시,거제시,양산시,의령군,함안군,창녕군,고성군,남해군,하동군,산청군,함양군,거창군,합천군,".split(",");
		String[] codes = "121,123,125,127,129,170,220,240,250,270,310,330,720,730,740,820,840,850,860,870,880,890,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 경북
	 * @return
	 */
	private  List<Map<String,String>> get47s(){
		String sidoCode = "47";
		String[] names = "포항시 남구,포항시 북구,경주시,김천시,안동시,구미시,영주시,영천시,상주시,문경시,경산시,군위군,의성군,청송군,영양군,영덕군,청도군,고령군,성주군,칠곡군,예천군,봉화군,울진군,울릉군,".split(",");
		String[] codes = "111,113,130,150,170,190,210,230,250,280,290,720,730,750,760,770,820,830,840,850,900,920,930,940,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 전남
	 * @return
	 */
	private  List<Map<String,String>> get46s(){
		String sidoCode = "46";
		String[] names = "목포시,여수시,순천시,나주시,광양시,담양군,곡성군,구례군,고흥군,보성군,화순군,장흥군,강진군,해남군,영암군,무안군,함평군,영광군,장성군,완도군,진도군,신안군,".split(",");
		String[] codes = "110,130,150,170,230,710,720,730,770,780,790,800,810,820,830,840,860,870,880,890,900,910,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 전북
	 * @return
	 */
	private  List<Map<String,String>> get45s(){
		String sidoCode = "45";
		String[] names = "전주시 완산구,전주시 덕진구,군산시,익산시,정읍시,남원시,김제시,완주군,진안군,무주군,장수군,임실군,순창군,고창군,부안군,".split(",");
		String[] codes = "111,113,130,140,180,190,210,710,720,730,740,750,770,790,800,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 충남
	 * @return
	 */
	private  List<Map<String,String>> get44s(){
		String sidoCode = "44";
		String[] names = "천안시 동남구,천안시 서북구,공주시,보령시,아산시,서산시,논산시,계룡시,당진시,금산군,부여군,서천군,청양군,홍성군,예산군,태안군,".split(",");
		String[] codes = "131,133,150,180,200,210,230,250,270,710,760,770,790,800,810,825,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 충북
	 * @return
	 */
	private  List<Map<String,String>> get43s(){
		String sidoCode = "43";
		String[] names = "청주시 상당구,청주시 서원구,청주시 흥덕구,청주시 청원구,충주시,제천시,청원군,보은군,옥천군,영동군,증평군,진천군,괴산군,음성군,단양군,".split(",");
		String[] codes = "111,112,113,114,130,150,710,720,730,740,745,750,760,770,800,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 강원
	 * @return
	 */
	private  List<Map<String,String>> get42s(){
		String sidoCode = "42";
		String[] names = "춘천시,원주시,강릉시,동해시,태백시,속초시,삼척시,홍천군,횡성군,영월군,평창군,정선군,철원군,화천군,양구군,인제군,고성군,양양군,".split(",");
		String[] codes = "110,130,150,170,190,210,230,720,730,750,760,770,780,790,800,810,820,830,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 경기
	 * @return
	 */
	private  List<Map<String,String>> get41s(){
		String sidoCode = "41";
		String[] names = "수원시 장안구,수원시 권선구,수원시 팔달구,수원시 영통구,성남시 수정구,성남시 중원구,성남시 분당구,의정부시,안양시 만안구,안양시 동안구,부천시 원미구,부천시 소사구,부천시 오정구,광명시,평택시,동두천시,안산시 상록구,안산시 단원구,고양시 덕양구,고양시 일산동구,고양시 일산서구,과천시,구리시,남양주시,오산시,시흥시,군포시,의왕시,하남시,용인시 처인구,용인시 기흥구,용인시 수지구,파주시,이천시,안성시,김포시,화성시,광주시,양주시,포천시,여주시,연천군,가평군,양평군,".split(",");
		String[] codes = "111,113,115,117,131,133,135,150,171,173,195,197,199,210,220,250,271,273,281,285,287,290,310,360,370,390,410,430,450,461,463,465,480,500,550,570,590,610,630,650,670,800,820,830,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 울산
	 * @return
	 */
	private  List<Map<String,String>> get31s(){
		String sidoCode = "31";
		String[] names = "중구,남구,동구,북구,울주군,".split(",");
		String[] codes = "110,140,170,200,710,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 대전
	 * @return
	 */
	private  List<Map<String,String>> get30s(){
		String sidoCode = "30";
		String[] names = "동구,중구,서구,유성구,대덕구,".split(",");
		String[] codes = "110,140,170,200,230,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 광주
	 * @return
	 */
	private  List<Map<String,String>> get29s(){
		String sidoCode = "29";
		String[] names = "동구,서구,남구,북구,광산구,".split(",");
		String[] codes = "110,140,155,170,200,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	
	/**
	 * 인천
	 * @return
	 */
	private  List<Map<String,String>> get28s(){
		String sidoCode = "28";
		String[] names = "중구,동구,미추홀구,연수구,남동구,부평구,계양구,서구,강화군,옹진군,".split(",");
		String[] codes = "110,140,177,185,200,237,245,260,710,720,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 대구
	 * @return
	 */
	private  List<Map<String,String>> get27s(){
		String sidoCode = "27";
		String[] names = "중구,동구,서구,남구,북구,수성구,달서구,달성군,".split(",");
		String[] codes = "110,140,170,200,230,260,290,710,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 부산
	 * @return
	 */
	private  List<Map<String,String>> get26s(){
		String sidoCode = "26";
		String[] names = "중구,서구,동구,영도구,부산진구,동래구,남구,북구,해운대구,사하구,금정구,강서구,연제구,수영구,사상구,기장군,".split(",");
		String[] codes = "110,140,170,200,230,260,290,320,350,380,410,440,470,500,530,710,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	/**
	 * 서울
	 * @return
	 */
	private  List<Map<String,String>> get11s(){
		String sidoCode = "11";
		String[] names = "종로구,중구,용산구,성동구,광진구,동대문구,중랑구,성북구,강북구,도봉구,노원구,은평구,서대문구,마포구,양천구,강서구,구로구,금천구,영등포구,동작구,관악구,서초구,강남구,송파구,강동구,".split(",");
		String[] codes = "110,140,170,200,215,230,260,290,305,320,350,380,410,440,470,500,530,545,560,590,620,650,680,710,740,".split(",");
		
		return createListOfMap(sidoCode, codes, names);
	}
	
	private List<Map<String,String>> createListOfMap(String sidoCode, String[] codes, String[] names){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		//
		for(int i=0; i<codes.length; i++) {
			if(CsUtil.isEmpty(codes[i])) {
				continue;
			}
			
			Map<String,String> d = new HashMap<String, String>();
			list.add(d);
			d.put("sidoCode", sidoCode);
			d.put("sigunguCode", sidoCode + codes[i]);
			d.put("sigunguName", names[i]);
		}
		
		//
		return list;
	}
	
}
