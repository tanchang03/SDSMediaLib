package com.sds.core.util;

import java.util.Collection;

@SuppressWarnings("rawtypes")
public interface TreeNode {

	public String get$ref();
	
	public String getName();
	
	public String getId();
	
	
	public Collection getChildren();
	
	
	
}
