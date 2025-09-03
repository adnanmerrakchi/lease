package com.devo.lease;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.devo.lease"})
@IncludeClassNamePatterns({".*Test$", ".*Tests$", ".*TestCase$"})
public class LeaseApplicationTests {
}
