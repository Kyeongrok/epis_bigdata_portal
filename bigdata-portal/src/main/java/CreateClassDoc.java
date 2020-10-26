import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 상세 클래스 설계서를 HTML로 작성하는 클래스 
 * 필요한 패키지/클래스 선택하여 javadoc생성후 실행
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 12. 19.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 12. 19.
 */
public class CreateClassDoc {
	// 최상위 패키지
	private static final String INPUT_DIRECTORY = "E:\\workspace\\epis-bigdata-portal\\doc1\\bigdata";
	private static final String  OUTPUT_FILE = "E:\\workspace\\epis-bigdata-portal\\doc1\\class-design-detail.html";

	public static void main(String[] args) throws Exception {
		
		File file = new File(OUTPUT_FILE);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
		
		File tofile = new File(OUTPUT_FILE);
		BufferedWriter output = new BufferedWriter(new FileWriter(tofile, true));
		output.write("<table style='border-collapse:collapse;'>");
		output.write("<colgroup><col width='150px'><col width='100px'><col width='150px'><col width='150px'><col width='250px'></colgroup>");
		output.close();
		
		listFilesForFolder(new File(INPUT_DIRECTORY));

		output = new BufferedWriter(new FileWriter(tofile, true));
		output.write("</table>");
		output.close();
	}

	private static void listFilesForFolder(final File folder) throws Exception {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				if (fileEntry.getName().equals("class-use"))
					continue;

				listFilesForFolder(fileEntry);
			} else {
				if (fileEntry.getName().startsWith("package-"))
					continue;

				createDoc(fileEntry);
			}
		}
	}

	private static ArrayList<String[]> createDocField(Elements ele) {
		ArrayList<String[]> list = new ArrayList<String[]>();

		for (Element el : ele) {
			String[] str = { "", "", "", "", "" };

			String field = el.select("pre").text();
			field = field.replaceAll("\\p{Z}", " ").trim();
			field = field.replaceAll("@[A-Za-z]+([(][^)]*[)])?", "").trim();
			field = field.replaceAll("[a-z0-9.]+[.]", "").trim();
			field = field.replaceAll("static ", "").trim();
			field = field.replaceAll("final ", "").trim();

			if (field.startsWith("private")) {
				str[1] = "private";
			} else if (field.startsWith("public")) {
				str[1] = "public";
			} else if (field.startsWith("protected")) {
				str[1] = "protected";
			}
			field = field.replaceAll("(private|public|protected) ", "").trim();

			String[] tmpField = field.split(" ");
			str[2] = tmpField[0];
			str[0] = tmpField[1];

			if (tmpField[0].equals("LOGGER"))
				str[4] = "로거";
			else if (tmpField[0].endsWith("Service"))
				str[4] = "서비스";
			else if (tmpField[0].endsWith("Mapper"))
				str[4] = "DB Mapper";
			
			list.add(str);
		}

		return list;
	}

	private static ArrayList<String[]> createDocConstructor(Elements ele) {
		ArrayList<String[]> list = new ArrayList<String[]>();

		for (Element el : ele) {
			String[] str = { "", "", "", "", "" };

			String field = el.select("pre").text();
			field = field.replaceAll("\\p{Z}", " ").trim();
			field = field.replaceAll("@[A-Za-z]+([(][^)]*[)])?", "").trim();
			field = field.replaceAll("(^|[ ,(<])[a-z0-9.]+[.]", "$1").trim();
			field = field.replaceAll("static ", "").trim();
			field = field.replaceAll("final ", "").trim();

			if (field.startsWith("private")) {
				str[1] = "private";
			} else if (field.startsWith("public")) {
				str[1] = "public";
			} else if (field.startsWith("protected")) {
				str[1] = "protected";
			}
			field = field.replaceAll("(private|public|protected) ", "").trim();

			String[] tmpField = field.split("\\(");
			str[0] = tmpField[0];

			Elements descEl = el.select("div.block");
			if (descEl.size() > 0) {
				descEl.select("pre").remove();
				str[4] = descEl.text();
			} else {
				str[4] = "생성자";
			}

			if (tmpField[1].trim().equals(")")) {
				list.add(str);
				continue;
			}

			String parameter = tmpField[1];

			int i = 0;
			int a = 0;
			int start = 0;
			ArrayList<String> newParam = new ArrayList<String>();
			while (i < parameter.length()) {
				char c = parameter.charAt(i);
				if (c == '<' || c == '[')
					a++;
				if (c == '>' || c == ']')
					a--;

				if ((c == ',' || c == ')') && a == 0) {
					newParam.add(parameter.substring(start, i).trim());
					start = i + 1;
				}

				i++;
			}

			parameter = "";
			for (String p : newParam) {
				p = p.replaceAll(" [a-zA-Z0-9_]+$", "").trim();
				parameter += p + ", ";
			}
			parameter = parameter.substring(0, parameter.length() - 2);
			str[2] = parameter;

			list.add(str);
		}

		return list;
	}

	private static ArrayList<String[]> createDocMethod(Elements ele) {
		ArrayList<String[]> list = new ArrayList<String[]>();

		for (Element el : ele) {
			String[] str = { "", "", "", "", "" };

			String field = el.select("pre").text();
			field = field.replaceAll("\\p{Z}", " ").trim();
			field = field.replaceAll("[\r\n\t ]+", " ").trim();
			field = field.replaceAll("@[A-Za-z]+([(][^)]*[)])?", "").trim();
			field = field.replaceAll("(^|[ ,(<])[a-z0-9.]+[.]", "$1").trim();
			field = field.replaceAll("static ", "").trim();
			field = field.replaceAll("final ", "").trim();
			field = field.replaceAll("throws .*", "").trim();

			if (field.startsWith("private")) {
				str[1] = "private";
			} else if (field.startsWith("public")) {
				str[1] = "public";
			} else if (field.startsWith("protected")) {
				str[1] = "protected";
			}
			field = field.replaceAll("(private|public|protected) ", "").trim();

			String[] tmpField = field.split("\\(");

			str[0] = tmpField[0].trim().replaceAll(".+ ", "").trim();
			str[3] = tmpField[0].trim().replaceAll(" [a-zA-Z0-9_]+$", "").trim();

			Elements descEl = el.select("div.block");
			if (descEl.size() > 0) {
				descEl.select("pre").remove();
				str[4] = descEl.text();
			}

			if (tmpField[1].trim().equals(")")) {
				list.add(str);
				continue;
			}
			String parameter = tmpField[1];
			
			int i = 0;
			int a = 0;
			int start = 0;
			ArrayList<String> newParam = new ArrayList<String>();
			while (i < parameter.length()) {
				char c = parameter.charAt(i);
				if (c == '<' || c == '[')
					a++;
				if (c == '>' || c == ']')
					a--;

				if ((c == ',' || c == ')') && a == 0) {
					newParam.add(parameter.substring(start, i).trim());
					start = i + 1;
				}

				i++;
			}

			parameter = "";
			for (String p : newParam) {
				p = p.replaceAll(" [a-zA-Z0-9_]+$", "").trim();
				parameter += p + ", ";
			}
			parameter = parameter.substring(0, parameter.length() - 2);
			str[2] = parameter;

			list.add(str);
		}

		return list;
	}

	private static void createDoc(final File file) {
		try {
			Document doc = Jsoup.parse(file, "UTF-8");

			HashMap<String, Object> info = new HashMap<String, Object>();
			ArrayList<String[]> fieldList = new ArrayList<String[]>();
			ArrayList<String[]> methodList = new ArrayList<String[]>();

			// 클래스명
			Elements ele = doc.select(".description span.typeNameLabel");
			String className = ele.get(0).text();
			info.put("className", className);

			ele = doc.select(".description div.block");
			if (ele.size() > 0) {
				ele.get(0).select("pre").remove();
				String classDesc = ele.get(0).text();
				info.put("classDesc", classDesc);
			} else {
				info.put("classDesc", "");
			}

			ele = doc.select(".header > .subTitle");
			String packageName = ele.get(0).text();
			info.put("packageName", packageName);
			
			
			ele = doc.select(".description span.typeNameLabel").parents();		
			Element elTmp = ele.get(0);
			elTmp.select(".typeNameLabel").remove();
			String extendsClass = elTmp.text();
			
			if(extendsClass.indexOf("extends") > -1) {
				extendsClass = extendsClass.replaceAll("\\p{Z}", " ").trim();
				extendsClass = extendsClass.replaceAll("[\r\n\t ]+", " ").trim();
				extendsClass = extendsClass.replaceAll("@[A-Za-z]+([(][^)]*[)])?", "").trim();
				extendsClass = extendsClass.replaceAll("(^|[ ,(<])[a-z0-9.]+[.]", "$1").trim();
				extendsClass = extendsClass.replaceAll(".* extends", " ");
				extendsClass = extendsClass.replaceAll("throws .*", " ");
				extendsClass = extendsClass.replaceAll("implements .*", "");
				extendsClass = extendsClass.trim();
				
				if(extendsClass.equals("Object")) extendsClass = "";
			} else {
				extendsClass = "";
			}
			
			info.put("extendsClass", extendsClass);

			// 필드
			Elements eleList = doc.select(".details > ul > li > ul.blockList");
			for (Element el : eleList) {
				ele = el.select("> li > ul");

				if (el.select("> li > a").is("[name='field.detail']")) {
					fieldList.addAll(createDocField(ele));
				} else if (el.select("> li > a").is("[name='constructor.detail']")) {
					methodList.addAll(createDocConstructor(ele));
				} else if (el.select("> li > a").is("[name='method.detail']")) {
					methodList.addAll(createDocMethod(ele));
				}
			}

			info.put("fieldList", fieldList);
			info.put("methodList", methodList);
			write(info);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void write(HashMap<String, Object> info) throws Exception {

		try {
			ArrayList<String[]> pList = (ArrayList<String[]>) info.get("fieldList");
			ArrayList<String[]> mList = (ArrayList<String[]>) info.get("methodList");
			String table = "";
			
			table += String.format("<tr><td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:left;border:1px solid #000000;' colspan='2'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:left;border:1px solid #000000;'>%s</td></tr>",
									"클래스 ID",
									"",
									"클래스명",
									tag((String) info.get("className")));

			table += String.format("<tr><td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:left;border:1px solid #000000;' colspan='2'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:left;border:1px solid #000000;'>%s</td></tr>",
									"패키지명",
									tag((String) info.get("packageName")),
									"상속클래스",
									tag((String) info.get("extendsClass")));
			
			table += String.format("<tr><td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:left;border:1px solid #000000;' colspan='4'>%s</td></tr>",
									"클래스설명",
									tag((String) info.get("classDesc")));
			
			table += "<tr><td colspan='5' style='text-align:center;background:#ddd;border:1px solid #000000;'>속성</td></tr>";
			table += String.format("<tr><td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td></tr>",
									"속성명",
									"가시성",
									"타입",
									"기본값",
									"설명");

			for (String[] str : pList) {
				table += String.format("<tr><td style='text-align:left;border:1px solid #000000;'>%s</td>"
										+ "<td style='text-align:center;border:1px solid #000000;'>%s</td>"
										+ "<td style='text-align:left;border:1px solid #000000;'>%s</td>"
										+ "<td style='text-align:left;border:1px solid #000000;'>%s</td>"
										+ "<td style='text-align:left;border:1px solid #000000;'>%s</td></tr>",
										tag(str[0]),
										tag(str[1]),
										tag(str[2]),
										tag(str[3]),
										tag(str[4]));
			}

			table += "<tr><td colspan='5' style='text-align:center;background:#ddd;border:1px solid #000000;'>오퍼레이션</td></tr>";
			table += String.format("<tr><td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td>"
									+ "<td style='text-align:center;background:#ddd;border:1px solid #000000;'>%s</td></tr>",
									"오퍼레이션명",
									"가시성",
									"파라미터",
									"반환타입",
									"설명");
			for (String[] str : mList) {
				table += String.format("<tr><td style='text-align:left;border:1px solid #000000;'>%s</td>"
										+ "<td style='text-align:center;border:1px solid #000000;'>%s</td>"
										+ "<td style='text-align:left;border:1px solid #000000;'>%s</td>"
										+ "<td style='text-align:left;border:1px solid #000000;'>%s</td>"
										+ "<td style='text-align:left;border:1px solid #000000;'>%s</td></tr>",
										tag(str[0]),
										tag(str[1]),
										tag(str[2]),
										tag(str[3]),
										tag(str[4]));
			}

			table += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";

			File tofile = new File(OUTPUT_FILE);
			BufferedWriter output = new BufferedWriter(new FileWriter(tofile, true));
			output.write(table);
			output.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String tag(String str) {
		return str.replace("<", "&lt;").replace(">", "&gt;");
	}
}
