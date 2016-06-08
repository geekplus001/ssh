package com.atguigu.struts2.validation.app;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class IDCardValidator extends FieldValidatorSupport {

	@Override
	public void validate(Object object) throws ValidationException {
		//1. 获取字段的名字和值
		String fieldName = getFieldName();
        Object value = this.getFieldValue(fieldName, object);
        
		//2. 验证
		IDCard idCard = new IDCard();
		boolean result = idCard.Verify((String)value);
        
		//3. 若验证失败, 则 ...
		if(!result){
			addFieldError(fieldName, object);
		}
		
	}

}
