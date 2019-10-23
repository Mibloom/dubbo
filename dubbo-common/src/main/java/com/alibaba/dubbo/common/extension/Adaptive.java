/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.common.extension;

import com.alibaba.dubbo.common.URL;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provide helpful information for {@link ExtensionLoader} to inject dependency extension instance.
 *
 * @see ExtensionLoader
 * @see URL
 */
/**
* NOTE-LPK:2019/10/21 18:16 自适应扩展机制。在调用扩展方法时再根据参数动态加载扩展，加载扩展是使用SPI加载
 * http://dubbo.apache.org/zh-cn/docs/source_code_guide/adaptive-extension.html
 * @Adaptive 注解使用在类上表示该实现类是接口的适配器，该实现类并不提供业务功能，而是通过该实现类选择某个途径的适配器(自适应拓展类),
 * 一般这个适配器是由我们自己实现的(手工编码),如 SpiExtensionFactory 和 SpringExtensionFactory
 * 注意：自己实现的(手工编码)是指不是由框架自动生成的，如 SpiExtensionFactory 虽然是框架里的类，但不是自动生成的，也属于手工编码。手工编码并不局限于使用框架的人自己写的类
 * @see com.alibaba.dubbo.common.extension.factory.AdaptiveExtensionFactory
 * @Adaptive 使用在接口的方法上，表示拓展的加载逻辑需由框架自动生成，dubbo会动态生成适配器类 如
 * @see com.alibaba.dubbo.remoting.Transporter
 *
 */
/**
* NOTE-LPK 2019/10/21 19:23 自适应拓展类的核心实现 ---- 在拓展接口的方法被调用时，通过 SPI 加载具体的拓展实现类，并调用拓展对象的同名方法
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Adaptive {
		/**
		 * Decide which target extension to be injected. The name of the target extension is decided by the parameter passed
		 * in the URL, and the parameter names are given by this method.
		 * <p>
		 * If the specified parameters are not found from {@link URL}, then the default extension will be used for
		 * dependency injection (specified in its interface's {@link SPI}).
		 * <p>
		 * For examples, given <code>String[] {"key1", "key2"}</code>:
		 * <ol>
		 * <li>find parameter 'key1' in URL, use its value as the extension's name</li>
		 * <li>try 'key2' for extension's name if 'key1' is not found (or its value is empty) in URL</li>
		 * <li>use default extension if 'key2' doesn't appear either</li>
		 * <li>otherwise, throw {@link IllegalStateException}</li>
		 * </ol>
		 * If default extension's name is not give on interface's {@link SPI}, then a name is generated from interface's
		 * class name with the rule: divide classname from capital char into several parts, and separate the parts with
		 * dot '.', for example: for {@code com.alibaba.dubbo.xxx.YyyInvokerWrapper}, its default name is
		 * <code>String[] {"yyy.invoker.wrapper"}</code>. This name will be used to search for parameter from URL.
		 *
		 * @return parameter key names in URL
		 */
		String[] value() default {};

}