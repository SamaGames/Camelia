package eu.carrade.amaury.Camelia.drawing.drawTools.core;


import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ToolLocator {

	/**
	 * The slot this tool will reside on.
	 *
	 * @return The slot. From 0 to 8; another value will be interpreted as 8 (don't do that!).
	 */
	int slot() default 0;

}
