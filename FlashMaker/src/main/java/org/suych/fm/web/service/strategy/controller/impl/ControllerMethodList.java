package org.suych.fm.web.service.strategy.controller.impl;

import static org.suych.fm.constant.ConstantJavaSyntax.ANNOTATIONS_REQUEST_MAPPING;
import static org.suych.fm.constant.ConstantJavaSyntax.COMMA;
import static org.suych.fm.constant.ConstantJavaSyntax.DOUBLE_QUOTATION;
import static org.suych.fm.constant.ConstantJavaSyntax.EQUAL_SIGN;
import static org.suych.fm.constant.ConstantJavaSyntax.INTEGER;
import static org.suych.fm.constant.ConstantJavaSyntax.LEFT_ANGLE_BRACKETS;
import static org.suych.fm.constant.ConstantJavaSyntax.LEFT_BRACE;
import static org.suych.fm.constant.ConstantJavaSyntax.LEFT_BRACKET;
import static org.suych.fm.constant.ConstantJavaSyntax.LINE;
import static org.suych.fm.constant.ConstantJavaSyntax.NULL;
import static org.suych.fm.constant.ConstantJavaSyntax.POINT;
import static org.suych.fm.constant.ConstantJavaSyntax.RETURN;
import static org.suych.fm.constant.ConstantJavaSyntax.RETURN_NEWLINE;
import static org.suych.fm.constant.ConstantJavaSyntax.RIGHT_ANGLE_BRACKETS;
import static org.suych.fm.constant.ConstantJavaSyntax.RIGHT_BRACE;
import static org.suych.fm.constant.ConstantJavaSyntax.RIGHT_BRACKET;
import static org.suych.fm.constant.ConstantJavaSyntax.SEMICOLON;
import static org.suych.fm.constant.ConstantJavaSyntax.SLASH;
import static org.suych.fm.constant.ConstantJavaSyntax.SPACE;
import static org.suych.fm.constant.ConstantJavaSyntax.STRING;
import static org.suych.fm.constant.ConstantJavaSyntax.TAB;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.suych.fm.base.BaseInfo;
import org.suych.fm.constant.ConstantClassName;
import org.suych.fm.constant.ConstantMethodAccessModifier;
import org.suych.fm.constant.ConstantMethodName;
import org.suych.fm.constant.ConstantParameterName;
import org.suych.fm.constant.ConstantParameterValue;
import org.suych.fm.constant.ConstantStrategyComponentName;
import org.suych.fm.constant.ConstantSuffix;
import org.suych.fm.tool.FileNameTool;
import org.suych.fm.util.generate.model.java.AnnotationStructure;
import org.suych.fm.util.generate.model.java.MethodStructure;
import org.suych.fm.util.generate.model.java.ParamterStructure;
import org.suych.fm.web.service.strategy.controller.IControllerMethod;

@Component(ConstantStrategyComponentName.CONTROLLER_LIST)
public class ControllerMethodList implements IControllerMethod {

	@Override
	public MethodStructure assemble() {
		MethodStructure result = new MethodStructure();
		// 注解
		List<AnnotationStructure> annotation = new ArrayList<AnnotationStructure>();
		AnnotationStructure requestMapping = new AnnotationStructure();

		// 注解属性
		Map<String, String> attribute = new LinkedHashMap<String, String>();
		String value = DOUBLE_QUOTATION + SLASH + ConstantMethodName.LIST + DOUBLE_QUOTATION;
		String method = LEFT_BRACE + SPACE + ConstantClassName.REQUEST_METHOD + POINT + ConstantParameterName.POST
				+ COMMA + SPACE + ConstantClassName.REQUEST_METHOD + POINT + ConstantParameterName.GET + SPACE
				+ RIGHT_BRACE;
		String produces = ConstantParameterValue.PRODUCES_VALUE;
		attribute.put(ConstantParameterName.VALUE, value);
		attribute.put(ConstantParameterName.METHOD, method);
		attribute.put(ConstantParameterName.PRODUCES, DOUBLE_QUOTATION + produces + DOUBLE_QUOTATION);
		requestMapping.setName(ANNOTATIONS_REQUEST_MAPPING);
		requestMapping.setAttribute(attribute);
		annotation.add(requestMapping);
		// 返回值
		String returnValue = STRING;
		// 方法名
		String methodName = ConstantMethodName.LIST;
		// 参数
		List<ParamterStructure> parameter = new ArrayList<ParamterStructure>();
		ParamterStructure p1 = new ParamterStructure();
		p1.setType(ConstantClassName.HTTP_SERVLET_REQUEST);
		p1.setName(ConstantParameterName.REQUEST);
		parameter.add(p1);
		// 方法体
		StringBuilder methodBody = new StringBuilder();
		// 第一句
		methodBody.append(TAB + TAB + INTEGER + SPACE + ConstantParameterName.PAGE_NUM + SPACE + EQUAL_SIGN + SPACE
				+ ConstantParameterValue.ONE + SEMICOLON + RETURN_NEWLINE);
		// 第二句
		methodBody.append(TAB + TAB + INTEGER + SPACE + ConstantParameterName.PAGE_SIZE + SPACE + EQUAL_SIGN + SPACE
				+ ConstantParameterValue.TWENTY + SEMICOLON + RETURN_NEWLINE);
		// 第三句
		String domainClassName = BaseInfo.getDomainClassName();
		String resultField = FileNameTool.firstLetterToLowerCase(domainClassName) + ConstantSuffix.S.getType();
		methodBody.append(
				TAB + TAB + ConstantClassName.PAGE_INFO + LEFT_ANGLE_BRACKETS + domainClassName + RIGHT_ANGLE_BRACKETS
						+ SPACE + resultField + SPACE + EQUAL_SIGN + SPACE + BaseInfo.getServiceInterfaceFieldName()
						+ POINT + ConstantMethodName.LIST + LEFT_BRACKET + ConstantParameterName.PAGE_NUM + COMMA
						+ SPACE + ConstantParameterName.PAGE_SIZE + RIGHT_BRACKET + SEMICOLON + RETURN_NEWLINE);
		// 第四句
		methodBody.append(TAB + TAB + resultField + POINT + ConstantMethodName.GET_LIST + LEFT_BRACKET + RIGHT_BRACKET
				+ POINT + ConstantMethodName.FOREACH + LEFT_BRACKET + LEFT_BRACKET + ConstantParameterName.N
				+ RIGHT_BRACKET + SPACE + LINE + RIGHT_ANGLE_BRACKETS + SPACE + LEFT_BRACE + RETURN_NEWLINE);
		// 第五句
		methodBody.append(TAB + TAB + TAB + ConstantClassName.SYSTEM + POINT + ConstantParameterName.OUT + POINT
				+ ConstantMethodName.PRINTLN + LEFT_BRACKET + ConstantParameterName.N + RIGHT_BRACKET + SEMICOLON
				+ RETURN_NEWLINE);
		// 第六句
		methodBody.append(TAB + TAB + RIGHT_BRACE + RIGHT_BRACKET + SEMICOLON + RETURN_NEWLINE);
		// 第七句
		methodBody.append(TAB + TAB + RETURN + SPACE + NULL + SEMICOLON + RETURN_NEWLINE);

		result.setAnnotation(annotation);
		result.setAccessModifier(ConstantMethodAccessModifier.PUBLIC);
		result.setReturnValue(returnValue);
		result.setName(methodName);
		result.setParameter(parameter);
		result.setMethodBody(methodBody.toString());
		return result;
	}

}
