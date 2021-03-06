package org.suych.fm.util.generate;

import static org.suych.fm.constant.ConstantJavaSyntax.ABSTRACT;
import static org.suych.fm.constant.ConstantJavaSyntax.CLASS;
import static org.suych.fm.constant.ConstantJavaSyntax.COMMA;
import static org.suych.fm.constant.ConstantJavaSyntax.DOUBLE_QUOTATION;
import static org.suych.fm.constant.ConstantJavaSyntax.EQUAL_SIGN;
import static org.suych.fm.constant.ConstantJavaSyntax.EXTENDS;
import static org.suych.fm.constant.ConstantJavaSyntax.FINAL;
import static org.suych.fm.constant.ConstantJavaSyntax.IMPLEMENTS;
import static org.suych.fm.constant.ConstantJavaSyntax.LEFT_BRACE;
import static org.suych.fm.constant.ConstantJavaSyntax.LEFT_BRACKET;
import static org.suych.fm.constant.ConstantJavaSyntax.PRIVATE;
import static org.suych.fm.constant.ConstantJavaSyntax.PROTECTED;
import static org.suych.fm.constant.ConstantJavaSyntax.PUBLIC;
import static org.suych.fm.constant.ConstantJavaSyntax.RETURN_NEWLINE;
import static org.suych.fm.constant.ConstantJavaSyntax.RIGHT_BRACE;
import static org.suych.fm.constant.ConstantJavaSyntax.RIGHT_BRACKET;
import static org.suych.fm.constant.ConstantJavaSyntax.SEMICOLON;
import static org.suych.fm.constant.ConstantJavaSyntax.SPACE;
import static org.suych.fm.constant.ConstantJavaSyntax.STATIC;
import static org.suych.fm.constant.ConstantJavaSyntax.SYNCHRONIZED;
import static org.suych.fm.constant.ConstantJavaSyntax.TAB;
import static org.suych.fm.constant.ConstantJavaSyntax.TRANSIENT;
import static org.suych.fm.constant.ConstantJavaSyntax.VOLATILE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.suych.fm.constant.ConstantClassAccessModifier;
import org.suych.fm.constant.ConstantClassNonAccessModifier;
import org.suych.fm.constant.ConstantFieldAccessModifier;
import org.suych.fm.constant.ConstantFieldNonAccessModifier;
import org.suych.fm.constant.ConstantMethodAccessModifier;
import org.suych.fm.constant.ConstantMethodNonAccessModifier;
import org.suych.fm.util.StringUtil;
import org.suych.fm.util.generate.model.java.AnnotationStructure;
import org.suych.fm.util.generate.model.java.ClassStructure;
import org.suych.fm.util.generate.model.java.FieldStructure;
import org.suych.fm.util.generate.model.java.MethodStructure;
import org.suych.fm.util.generate.model.java.ParamterStructure;

public class GenerateClassUtil extends GenerateCommonUtil {

	public static void generate(ClassStructure cs) {
		File file = getFile(cs);
		try (FileWriter fw = new FileWriter(file)) {
			// 输出至文件
			print2File(fw, cs);
			fw.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void print2File(FileWriter fw, ClassStructure cs) throws IOException {
		// 1.本地包名
		printLocalPackage(fw, cs.getLocalPackage());
		// 2.引入包名
		printImportPackage(fw, cs.getImportPackage());
		// 3.类注释
		printComments(fw, cs.getComments(), "");
		// 4.类注解
		printAnnotation(fw, cs.getAnnotation(), "");
		// 5.修饰符+类名+继承+接口
		printModifierAndClassNameAndExtendsAndInterface(fw, cs);
		// 6.字段
		printField(fw, cs);
		// 7.方法
		printMethod(fw, cs);
		fw.write(RIGHT_BRACE);
	}

	/**
	 * 输出修饰符、类名、继承的类及实现的接口
	 * 
	 * @param fw
	 * @param cs
	 * @throws IOException
	 */
	private static void printModifierAndClassNameAndExtendsAndInterface(FileWriter fw, ClassStructure cs)
			throws IOException {
		String name = cs.getName();
		ConstantClassAccessModifier accessModifier = cs.getAcessModifier();
		ConstantClassNonAccessModifier nonAccessModifier = cs.getNonAccessModifier();
		// 访问控制修饰符
		if (accessModifier == ConstantClassAccessModifier.PUBLIC) {
			fw.write(PUBLIC + SPACE);
		}
		// 非访问控制修饰符
		if (nonAccessModifier == ConstantClassNonAccessModifier.ABSTRACT) {
			fw.write(ABSTRACT + SPACE);
		}
		if (nonAccessModifier == ConstantClassNonAccessModifier.FINAL) {
			fw.write(FINAL + SPACE);
		}
		// 类名
		fw.write(CLASS + SPACE + name + SPACE);
		// 继承
		if (cs.getExtendsClass() != null && cs.getExtendsClass()) {
			String baseClassName = cs.getBaseClassName();
			fw.write(EXTENDS + SPACE + baseClassName + SPACE);
		}
		// 接口
		if (cs.getImplementInterface() != null && cs.getImplementInterface() && cs.getInterfaceName() != null
				&& cs.getInterfaceName().size() != 0) {
			List<String> interfaceNameList = cs.getInterfaceName();
			fw.write(IMPLEMENTS + SPACE);
			StringBuilder interfaceNameTotal = new StringBuilder();
			for (String interfaceName : interfaceNameList) {
				interfaceNameTotal.append(interfaceName + COMMA + SPACE);
			}
			fw.write(interfaceNameTotal.substring(0, interfaceNameTotal.length() - 2) + SPACE);
		}
		fw.write(LEFT_BRACE + RETURN_NEWLINE);
	}

	/**
	 * 输出字段信息
	 * 
	 * @param fw
	 * @param cs
	 * @throws IOException
	 */
	private static void printField(FileWriter fw, ClassStructure cs) throws IOException {
		List<FieldStructure> fieldStructures = cs.getField();
		for (FieldStructure field : fieldStructures) {
			fw.write(RETURN_NEWLINE);
			// 注释
			printComments(fw, field.getComments(), TAB);
			// 注解
			printAnnotation(fw, field.getAnnotation(), TAB);
			// 访问控制修饰符
			fw.write(TAB);
			ConstantFieldAccessModifier fieldAccessModifier = field.getAccessModifier();
			if (fieldAccessModifier == ConstantFieldAccessModifier.PUBLIC) {
				fw.write(PUBLIC + SPACE);
			} else if (fieldAccessModifier == ConstantFieldAccessModifier.PRIVATE) {
				fw.write(PRIVATE + SPACE);
			} else if (fieldAccessModifier == ConstantFieldAccessModifier.PROTECTED) {
				fw.write(PROTECTED + SPACE);
			}
			// 非访问控制修饰符
			List<ConstantFieldNonAccessModifier> fieldNonAccessModifier = field.getNonAccessModifier();
			if (fieldNonAccessModifier != null) {
				for (ConstantFieldNonAccessModifier m : fieldNonAccessModifier) {
					if (m == ConstantFieldNonAccessModifier.STATIC) {
						fw.write(STATIC + SPACE);
					} else if (m == ConstantFieldNonAccessModifier.FINAL) {
						fw.write(FINAL + SPACE);
					} else if (m == ConstantFieldNonAccessModifier.VOLATILE) {
						fw.write(VOLATILE + SPACE);
					} else if (m == ConstantFieldNonAccessModifier.TRANSIENT) {
						fw.write(TRANSIENT + SPACE);
					}
				}
			}
			// java类型
			String javaType = field.getJavaType();
			// 字段名
			String fieldName = field.getName();
			fw.write(javaType + SPACE + fieldName);
			// 是否初始化
			if (field.getInitialization()) {
				String initializationValue = field.getInitializationValue();
				fw.write(SPACE + EQUAL_SIGN + SPACE + initializationValue);
			}
			fw.write(SEMICOLON + RETURN_NEWLINE);
		}
		fw.write(RETURN_NEWLINE);
	}

	/**
	 * 输出方法信息
	 * 
	 * @param fw
	 * @param cs
	 * @throws IOException
	 */
	private static void printMethod(FileWriter fw, ClassStructure cs) throws IOException {
		List<MethodStructure> methodStructures = cs.getMethod();
		if (methodStructures == null) {
			return;
		}
		for (MethodStructure method : methodStructures) {
			// 注释
			printComments(fw, method.getComments(), TAB);
			// 注解
			printAnnotation(fw, method.getAnnotation(), TAB);
			// 访问控制修饰符
			fw.write(TAB);
			ConstantMethodAccessModifier methodAccessModifier = method.getAccessModifier();
			if (methodAccessModifier == ConstantMethodAccessModifier.PUBLIC) {
				fw.write(PUBLIC + SPACE);
			} else if (methodAccessModifier == ConstantMethodAccessModifier.PRIVATE) {
				fw.write(PRIVATE + SPACE);
			} else if (methodAccessModifier == ConstantMethodAccessModifier.PROTECTED) {
				fw.write(PROTECTED + SPACE);
			}
			// 非访问控制修饰符
			List<ConstantMethodNonAccessModifier> methodNonAccessModifiers = method.getNonAccessModifier();
			if (methodNonAccessModifiers != null) {
				for (ConstantMethodNonAccessModifier m : methodNonAccessModifiers) {
					if (m == ConstantMethodNonAccessModifier.STATIC) {
						fw.write(STATIC + SPACE);
					} else if (m == ConstantMethodNonAccessModifier.FINAL) {
						fw.write(FINAL + SPACE);
					} else if (m == ConstantMethodNonAccessModifier.ABSTRACT) {
						fw.write(ABSTRACT + SPACE);
					} else if (m == ConstantMethodNonAccessModifier.SYNCHRONIZED) {
						fw.write(SYNCHRONIZED + SPACE);
					}
				}
			}
			// 返回值
			String returnValue = method.getReturnValue();
			fw.write(returnValue + SPACE);
			// 方法名
			String methodName = method.getName();
			fw.write(methodName);
			// 参数<类型，参数名>
			fw.write(LEFT_BRACKET);
			List<ParamterStructure> parameters = method.getParameter();
			if (parameters != null && parameters.size() > 0) {
				for (int i = 0, j = parameters.size(); i < j; i++) {
					ParamterStructure parameter = parameters.get(i);
					String javaType = parameter.getType();
					String fieldName = parameter.getName();
					if (parameter.getAnnotation() != null) {
						printAnnotation4MethodParam(fw, parameter.getAnnotation(), "");
						fw.write(SPACE);
					}
					fw.write(javaType + SPACE + fieldName);
					if (i != j - 1) {
						fw.write(COMMA + SPACE);
					}
				}
			}
			fw.write(RIGHT_BRACKET + SPACE + LEFT_BRACE + RETURN_NEWLINE);
			// 方法体
			String methodBody = method.getMethodBody();
			fw.write(methodBody);
			fw.write(TAB + RIGHT_BRACE + RETURN_NEWLINE);
			fw.write(RETURN_NEWLINE);
		}
	}

	/**
	 * 打印方法参数的注解
	 * 
	 * @param fw
	 * @param annotations
	 * @param indent 缩进
	 * @throws IOException
	 */
	protected static void printAnnotation4MethodParam(FileWriter fw, AnnotationStructure annotation, String indent)
			throws IOException {
		if (annotation == null) {
			return;
		}
		String name = annotation.getName();
		fw.write(indent + name);
		String annotationValue = StringUtil.null2Empty(annotation.getValue());
		if (!"".equals(annotationValue)) {
			// 直接赋值
			fw.write(LEFT_BRACKET);
			fw.write(DOUBLE_QUOTATION + annotationValue + DOUBLE_QUOTATION);
			fw.write(RIGHT_BRACKET);
		} else {
			// 属性赋值
			Map<String, String> attribute = annotation.getAttribute();
			if (attribute != null && attribute.size() > 0) {
				fw.write(LEFT_BRACKET);
				int i = 0;
				int entryNum = attribute.entrySet().size();
				for (Entry<String, String> entry : attribute.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					fw.write(key + SPACE + EQUAL_SIGN + SPACE + value);
					if (i != entryNum - 1) {
						fw.write(COMMA + SPACE);
					}
					i++;
				}
				fw.write(RIGHT_BRACKET);
			}
		}
	}

}
