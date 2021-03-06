/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.wellness.util;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.module.kenyacore.test.TestUtils;

/**
 * Tests for {@link EmrUtils}
 */
public class BuildPropertiesTest {

	private BuildProperties properties = new BuildProperties();

	/**
	 * @see BuildProperties#setBuildDate(java.util.Date)
	 */
	@Test
	public void setBuildDate() {
		Assert.assertNull(properties.getBuildDate());
		properties.setBuildDate(TestUtils.date(2012, 1, 1));
		Assert.assertEquals(TestUtils.date(2012, 1, 1), properties.getBuildDate());
	}

	/**
	 * @see BuildProperties#setDeveloper(String)
	 */
	@Test
	public void setDeveloper() {
		Assert.assertNull(properties.getDeveloper());
		properties.setDeveloper("Mr Test");
		Assert.assertEquals("Mr Test", properties.getDeveloper());
	}
}