/*
 * Copyright 2011 Moxie Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.moxieapps.gwt.highcharts.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;

/**
 * Represents a single data point that can be added to a {@link Series}.  As an extension
 * of {@link Configurable} each point instance can also optionally have configuration options set on it.
 * Standard example:
 * <code><pre>
 *  chart.addSeries(chart.createSeries()
 *     .setName("Browser share")
 *     .setPoints(new Point[] {
 *        new Point(15, 45.0),
 *        new Point(25, 26.8),
 *        new Point(35, 12.8),
 *        new Point(46, 8.5),
 *        new Point(55, 6.2),
 *        new Point(65, 0.7)
 *     })
 * );
 * </pre></code>
 * </p>
 * Advanced pie chart example (where the points represent categories and values for each category):
 * <code><pre>
 *  chart.addSeries(chart.createSeries()
 *     .setName("Browser share")
 *     .setPoints(new Point[]{
 *        new Point("Firefox", 45.0),
 *        new Point("IE", 26.8),
 *        new Point("Chrome", 12.8)
 *            .setSliced(true)
 *            .setSelected(true),
 *        new Point("Safari", 8.5),
 *        new Point("Opera", 6.2),
 *        new Point("Others", 0.7)
 *     })
 * );
 * </pre></code>
 *
 * @author squinn@moxiegroup.com (Shawn Quinn)
 * @since 1.0.0
 */
public class Point extends Configurable<Point> {

    private Number y;
    private Number x;

    /**
     * Create a new point, setting only the Y axis value that the point should be
     * rendered at within the series.
     *
     * @param y The Y value that the point should be rendered at within the series.
     */
    public Point(Number y) {
        this.y = y;
    }

    /**
     * Create a new point, setting both the value that the point should be rendered
     * at on the X and Y axis within the series.
     *
     * @param x The X value that the point should be rendered at within the series.
     * @param y The Y value that the point should be rendered at within the series.
     */
    public Point(Number x, Number y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new point, setting the Y axis value that the point should be
     * rendered at within the series as well as the "name" property of the point.
     *
     * @param name The value to set as the "property" of the point.
     * @param y    The Y value that the point should be rendered at within the series.
     */
    public Point(String name, Number y) {
        setName(name);
        this.y = y;
    }

    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
    private JavaScriptObject nativePoint;

    /**
     * This constructor is intended for internal use, and provides the ability to construct a GWT Point
     * instance from the native JS Point instance managed within Highcharts.
     *
     * @param nativePoint The native javascript object containing the details of the point
     */
    public Point(JavaScriptObject nativePoint) {
        this.nativePoint = nativePoint;
    }

    /**
     * Retrieve the Y value of where point should be rendered at within the series.
     *
     * @return The Y value of the point (should always be non null).
     */
    public Number getY() {
        if (this.nativePoint != null) {
            return nativeGetNumber(this.nativePoint, "y");
        } else {
            return y;
        }
    }

    /**
     * Retrieve the X value of where point should be rendered at within the series.
     *
     * @return The X value of the point, or null if no X value was set.
     */
    public Number getX() {
        if (this.nativePoint != null) {
            return nativeGetNumber(this.nativePoint, "x");
        } else {
            return x;
        }
    }

    /**
     * Convenience method for setting the 'color' option of the point.  Equivalent to:
     * <pre><code>
     *     point.setOption("color", "#CC0000");
     * </code></pre>
     * Individual color for the point. Defaults to null.
     *
     * @param color The value to set as the point's color.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     */
    public Point setColor(String color) {
        return this.setOption("color", color);
    }

    /**
     * Override the individual point marker for the point. Defaults to null.  E.g.
     * <pre><code>
     *    new Point(10, 30)
     *      .setMarker(
     *         new Marker()
     *            .setEnabled(true)
     *            .setFillColor("#CC0000")
     *            .setRadius(4)
     *      );
     * <p/>
     * </code></pre>
     * Note that this method is intended to be used to override the marker options of one
     * particular point.  If you instead want to control the marker options for the entire
     * series, use the {@link org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions#setMarker(Marker)}
     * method instead.
     *
     * @param marker The override marker options for this particular point, or null to
     *               simply use the whatever default marker options have been applied to whole series
     *               via {@link org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions#setMarker(Marker)}
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point setMarker(Marker marker) {
        return this.setOption("marker", marker != null ? marker.getOptions() : null);
    }

    private String name;

    /**
     * Convenience method for setting the 'name' option of the point.  Equivalent to:
     * <pre><code>
     *     point.setOption("name", "Green Bears");
     * </code></pre>
     * The name of the point as shown in the legend, tooltip, dataLabel etc. Defaults to "".
     *
     * @param name The value to set as the point's name.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     */
    public Point setName(String name) {
        this.name = name;
        return this.setOption("name", name);
    }

    /**
     * Return the name property that was set on this point, or null if no name was set.
     *
     * @return The 'name' property that was set on this point (likely via {@link #setName(String)},
     *         or null if no name was set.
     */
    public String getName() {
        if (this.nativePoint != null) {
            return nativeGetString(this.nativePoint, "name");
        } else {
            return this.name;
        }
    }

    private boolean selected = false;

    /**
     * Convenience method for setting the 'selected' option of the point.  Equivalent to:
     * <pre><code>
     *     point.setOption("selected", true);
     * </code></pre>
     * Whether the point is selected or not.
     *
     * @param selected Whether the point is selected or not.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     */
    public Point setSelected(boolean selected) {
        this.selected = selected;
        return this.setOption("selected", selected);
    }

    private boolean sliced = false;

    /**
     * Convenience method for setting the 'sliced' option of the point.  Equivalent to:
     * <pre><code>
     *     point.setOption("sliced", false);
     * </code></pre>
     * Pie series only. Whether to display a slice offset from the center. Defaults to false.
     *
     * @param sliced The value to set as the point's 'sliced' option.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     */
    public Point setSliced(boolean sliced) {
        this.sliced = sliced;
        return this.setOption("sliced", sliced);
    }

    /**
     * Store some arbitrary data on the point.  As the user interacts with the chart various events
     * are fired (such as {@link org.moxieapps.gwt.highcharts.client.events.PointClickEvent}), which
     * include a reference to the Point instance that the event was fired on.  The Point instances
     * that are returned from the live rendered chart are actually different instances that the versions
     * that were originally added to the chart via one of the Series addPoint() or setPoints() methods,
     * as Highcharts has its own internal representation of all of the point objects.  Therefore,
     * if you need to track some additional information with each point (beyond its axis values)
     * that you can later access when an event on the point is fired, this method provides a convenient
     * way for you to store some arbitrary data on the point which will then later be available
     * via the {@link #getUserData()} method.
     *
     * @param userData Some arbitrary data that to store with the point that will be available
     *                 later whenever the point instance is retrieved after the chart is rendered.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point setUserData(JSONObject userData) {
        return this.setOption("userData", userData);
    }

    /**
     * Retrieve any arbitrary data that was stored along with the point
     * (via the {@link #setUserData(JSONObject)} method) when it was originally added to a series
     *
     * @return The arbitrary data that was stored with the point when it was first added to the series,
     *         or null if no such data was set.
     * @since 1.1.0
     */
    public JSONObject getUserData() {
        if(nativePoint != null) {
            JavaScriptObject nativeUserData = nativeGetUserData(nativePoint);
            return nativeUserData != null ? new JSONObject(nativeUserData) : null;
        } else {
            return this.getOptions() != null ? (JSONObject)this.getOptions().get("userData") : null;
        }
    }

    /**
     * Remove the point from the series, automatically redrawing the chart using the default
     * animation options. <p/>
     * Note that this method is only relevant on Point instance that are obtained from the chart
     * after it has been rendered, such as via an event or dynamically retrieving the points of
     * a series.
     *
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point remove() {
        return this.remove(true, true);
    }

    /**
     * Remove the point from the series, explicitly controlling whether the chart is redrawn
     * and/or animated or not. <p/>
     * Note that this method is only relevant on Point instance that are obtained from the chart
     * after it has been rendered, such as via an event or dynamically retrieving the points of
     * a series.
     *
     * @param redraw    Whether to redraw the chart after the point is removed. When removing more than one
     *                  point, it is highly recommended that the redraw option be set to false, and instead
     *                  {@link Chart#redraw()} is explicitly called after the removing of points is finished.
     * @param animation Defaults to true. When true, the graph will be animated with default animation options.
     *                  Note, see the {@link #remove(boolean, Animation)} method
     *                  for more control over how the animation will run.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point remove(boolean redraw, boolean animation) {
        return this.remove(redraw, animation ? new Animation() : null);
    }

    /**
     * Remove the point from the series, explicitly controlling whether the chart is redrawn
     * and the details of the animation options. <p/>
     * Note that this method is only relevant on Point instance that are obtained from the chart
     * after it has been rendered, such as via an event or dynamically retrieving the points of
     * a series.
     *
     * @param redraw    Whether to redraw the chart after the point is removed. When removing more than one
     *                  point, it is highly recommended that the redraw option be set to false, and instead
     *                  {@link Chart#redraw()} is explicitly called after the removing of points is finished.
     * @param animation The custom animation to use when removing the point from the series.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point remove(boolean redraw, Animation animation) {
        if (this.nativePoint != null) {
            if (animation == null || animation.getOptions() == null) {
                nativeRemove(this.nativePoint, redraw, animation != null);
            } else {
                nativeRemove(this.nativePoint, redraw, animation.getOptions().getJavaScriptObject());
            }
        }
        // Nothing to do if this point isn't connected to a Highcharts JS point instance
        return this;
    }

    /**
     * Select or unselect the point.  See the {@link #selectToggle(boolean)} method for other options.
     *
     * @param select     When true, the point is selected. If you'd instead like to simply toggle the selection
     *                   state see the {@link #selectToggle(boolean)} method instead.
     * @param accumulate When true, the selection is added to other selected points. When false, other
     *                   selected points are deselected. Internally in Highcharts, selected points
     *                   are accumulated on Control, Shift or Cmd clicking the point.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point select(boolean select, boolean accumulate) {
        if (this.nativePoint != null) {
            nativeSelect(this.nativePoint, select, accumulate);
        } else {
            setSelected(select);
        }
        return this;
    }

    /**
     * Toggle the selection state of the point.  See the {@link #select(boolean, boolean)} method for other options.
     *
     * @param accumulate When true, the selection is added to other selected points. When false, other
     *                   selected points are deselected. Internally in Highcharts, selected points
     *                   are accumulated on Control, Shift or Cmd clicking the point.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point selectToggle(boolean accumulate) {
        if (this.nativePoint != null) {
            nativeSelectToggle(this.nativePoint, accumulate);
        } else {
            setSelected(!selected);
        }
        return this;
    }

    /**
     * Slice out or set back in a pie chart slice, automatically redrawing the chart with the default
     * animation options. This is the default way of Highcharts to visualize that a pie point is
     * selected.  See the {@link #sliceToggle()} method as well.
     *
     * @param sliced When true, the point is sliced out. When false, the point is set in.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point slice(boolean sliced) {
        return this.slice(sliced, true, true);
    }

    /**
     * Slice out or set back in a pie chart slice, controlling whether or not the chart will be
     * automatically redraw or not. This is the default way of Highcharts to visualize that a pie point is
     * selected.  See the {@link #sliceToggle(boolean, boolean)} method as well.
     *
     * @param sliced    When true, the point is sliced out. When false, the point is set in.
     * @param redraw    Whether to redraw the chart after the point is sliced. When slicing more than one
     *                  point, it is highly recommended that the redraw option be set to false, and instead
     *                  {@link Chart#redraw()} is explicitly called after the slicing of points is finished.
     * @param animation Defaults to true. When true, the graph will be animated with default animation options.
     *                  Note, also see the {@link #slice(boolean, boolean, Animation)} method
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point slice(boolean sliced, boolean redraw, boolean animation) {
        return this.slice(sliced, redraw, animation ? new Animation() : null);
    }

    /**
     * Slice out or set back in a pie chart slice, controlling whether or not the chart will be
     * automatically redraw or not. This is the default way of Highcharts to visualize that a pie point is
     * selected.  See the {@link #sliceToggle(boolean, Animation)} method as well.
     *
     * @param sliced    When true, the point is sliced out. When false, the point is set in.
     * @param redraw    Whether to redraw the chart after the point is sliced. When slicing more than one
     *                  point, it is highly recommended that the redraw option be set to false, and instead
     *                  {@link Chart#redraw()} is explicitly called after the slicing of points is finished.
     * @param animation The custom animation to use when slicing the point ing the series.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point slice(boolean sliced, boolean redraw, Animation animation) {
        if (this.nativePoint != null) {
            if (animation == null || animation.getOptions() == null) {
                nativeSlice(this.nativePoint, sliced, redraw, animation != null);
            } else {
                nativeSlice(this.nativePoint, sliced, redraw, animation.getOptions().getJavaScriptObject());
            }
        } else {
            setSliced(sliced);
        }
        return this;
    }

    /**
     * Toggle slicing out or set back in a pie chart slice, automatically redrawing the chart with the default
     * animation options. This is the default way of Highcharts to visualize that a pie point is
     * selected.  See the {@link #slice(boolean)} method as well.
     *
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point sliceToggle() {
        return this.sliceToggle(true, true);
    }

    /**
     * Toggle slicing out or set back in a pie chart slice, controlling whether or not the chart will be
     * automatically redraw or not. This is the default way of Highcharts to visualize that a pie point is
     * selected.  See the {@link #slice(boolean, boolean, boolean)} method as well.
     *
     * @param redraw    Whether to redraw the chart after the point is sliced. When slicing more than one
     *                  point, it is highly recommended that the redraw option be set to false, and instead
     *                  {@link Chart#redraw()} is explicitly called after the slicing of points is finished.
     * @param animation Defaults to true. When true, the graph will be animated with default animation options.
     *                  Note, also see the {@link #slice(boolean, boolean, Animation)} method
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point sliceToggle(boolean redraw, boolean animation) {
        return this.sliceToggle(redraw, animation ? new Animation() : null);
    }

    /**
     * Toggle slicing out or set back in a pie chart slice, controlling whether or not the chart will be
     * automatically redraw or not. This is the default way of Highcharts to visualize that a pie point is
     * selected.  See the {@link #slice(boolean, boolean, Animation)} method as well.
     *
     * @param redraw    Whether to redraw the chart after the point is sliced. When slicing more than one
     *                  point, it is highly recommended that the redraw option be set to false, and instead
     *                  {@link Chart#redraw()} is explicitly called after the slicing of points is finished.
     * @param animation The custom animation to use when slicing the point ing the series.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point sliceToggle(boolean redraw, Animation animation) {
        if (this.nativePoint != null) {
            if (animation == null || animation.getOptions() == null) {
                nativeSlice(this.nativePoint, redraw, animation != null);
            } else {
                nativeSlice(this.nativePoint, redraw, animation.getOptions().getJavaScriptObject());
            }
        } else {
            setSliced(!sliced);
        }
        return this;
    }

    /**
     * Update the point with the new values, automatically redrawing
     * the chart with the default animation options.
     *
     * @param y The new y value for the point.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point update(Number y) {
        return this.update(new Point(y));
    }

    /**
     * Update the point with the new values, automatically redrawing
     * the chart with the default animation options.
     *
     * @param x The new x value for the point.
     * @param y The new value for the point.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point update(Number x, Number y) {
        return this.update(new Point(x, y));
    }

    /**
     * Update the point with the new values, specifying whether or not the chart should be automatically
     * redrawn with the new values.
     *
     * @param y      The new y value for the point.
     * @param redraw Whether to redraw the chart after the point is updated. When updating more than one
     *               point, it is highly recommended that the redraw option be set to false, and instead
     *               {@link Chart#redraw()} is explicitly called after the updating of points is finished.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point update(Number y, boolean redraw) {
        return this.update(new Point(y), redraw, true);
    }

    /**
     * Update the point with the new values, specifying whether or not the chart should be automatically
     * redrawn with the new values.
     *
     * @param x      The new x value for the point.
     * @param y      The new value for the point.
     * @param redraw Whether to redraw the chart after the point is updated. When updating more than one
     *               point, it is highly recommended that the redraw option be set to false, and instead
     *               {@link Chart#redraw()} is explicitly called after the updating of points is finished.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point update(Number x, Number y, boolean redraw) {
        return this.update(new Point(x, y), redraw, true);
    }

    /**
     * Update the point with the new values and options from the given point, automatically redrawing
     * the chart with the default animation options.
     *
     * @param pointOptions The point instance from which the new values and options will be retrieved.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point update(Point pointOptions) {
        return this.update(pointOptions, true, true);
    }

    /**
     * Update the point with the new values and options from the given point, specifying if the chart
     * should be automatically redrawn and animated or not.
     *
     * @param pointOptions The point instance from which the new values and options will be retrieved.
     * @param redraw       Whether to redraw the chart after the point is updated. When updating more than one
     *                     point, it is highly recommended that the redraw option be set to false, and instead
     *                     {@link Chart#redraw()} is explicitly called after the updating of points is finished.
     * @param animation    Defaults to true. When true, the graph will be animated with default animation options.
     *                     Note, see the {@link #update(Point, boolean, Animation)} method
     *                     for more control over how the animation will run.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point update(Point pointOptions, boolean redraw, boolean animation) {
        return this.update(pointOptions, redraw, animation ? new Animation() : null);
    }

    /**
     * Update the point with the new values and options from the given point, specifying if the chart
     * should be automatically redrawn and animated or not.
     *
     * @param pointOptions The point instance from which the new values and options will be retrieved.
     * @param redraw       Whether to redraw the chart after the point is updating. When updating more than one
     *                     point, it is highly recommended that the redraw option be set to false, and instead
     *                     {@link Chart#redraw()} is explicitly called after the updating of points is finished.
     * @param animation    The custom animation to use when updating the point in the series.
     * @return A reference to this {@link Point} instance for convenient method chaining.
     * @since 1.1.0
     */
    public Point update(Point pointOptions, boolean redraw, Animation animation) {
        if (this.nativePoint != null) {
            if (animation == null || animation.getOptions() == null) {
                if (pointOptions.isSingleValue()) {
                    nativeUpdate(this.nativePoint, pointOptions.getY(), redraw, animation != null);
                } else {
                    nativeUpdate(this.nativePoint, Series.convertPointToJavaScriptObject(pointOptions), redraw, animation != null);
                }
            } else {
                if (pointOptions.isSingleValue()) {
                    nativeUpdate(this.nativePoint, pointOptions.getY(), redraw, animation.getOptions().getJavaScriptObject());
                } else {
                    nativeUpdate(this.nativePoint, Series.convertPointToJavaScriptObject(pointOptions), redraw, animation.getOptions().getJavaScriptObject());
                }
            }
        } else {
            this.x = pointOptions.x;
            this.y = pointOptions.y;
            this.name = pointOptions.name;
            this.selected = pointOptions.selected;
            this.sliced = pointOptions.sliced;
            for (String key : pointOptions.getOptions().keySet()) {
                this.setOption(key, pointOptions.getOptions().get(key));
            }
        }
        return this;
    }

    // Purposefully package scope
    boolean isSingleValue() {
        return this.getOptions() == null && this.getX() == null;
    }

    private static native double nativeGetNumber(JavaScriptObject point, String key) /*-{
        return point[key];
    }-*/;

    private static native String nativeGetString(JavaScriptObject point, String name) /*-{
        return point[key];
    }-*/;

    private static native void nativeRemove(JavaScriptObject point, boolean redraw, boolean animation) /*-{
        point.remove(redraw, animation);
    }-*/;

    private static native void nativeRemove(JavaScriptObject point, boolean redraw, JavaScriptObject animation) /*-{
        point.remove(redraw, animation);
    }-*/;

    private static native void nativeSelect(JavaScriptObject point, boolean select, boolean accumulate) /*-{
        point.select(select, accumulate);
    }-*/;

    private static native void nativeSelectToggle(JavaScriptObject point, boolean accumulate) /*-{
        point.select(null, accumulate);
    }-*/;

    private static native void nativeSlice(JavaScriptObject point, boolean sliced, boolean redraw, JavaScriptObject animation) /*-{
        point.slice(sliced, redraw, animation);
    }-*/;

    private static native void nativeSlice(JavaScriptObject point, boolean sliced, boolean redraw, boolean animation) /*-{
        point.slice(sliced, redraw, animation);
    }-*/;

    private static native void nativeSlice(JavaScriptObject point, boolean redraw, JavaScriptObject animation) /*-{
        point.slice(null, redraw, animation);
    }-*/;

    private static native void nativeSlice(JavaScriptObject point, boolean redraw, boolean animation) /*-{
        point.slice(null, redraw, animation);
    }-*/;

    private static native void nativeUpdate(JavaScriptObject point, JavaScriptObject options, boolean redraw, JavaScriptObject animation) /*-{
        point.update(options, redraw, animation);
    }-*/;

    private static native void nativeUpdate(JavaScriptObject point, Number y, boolean redraw, JavaScriptObject animation) /*-{
        point.update(y, redraw, animation);
    }-*/;

    private static native void nativeUpdate(JavaScriptObject point, JavaScriptObject options, boolean redraw, boolean animation) /*-{
        point.update(options, redraw, animation);
    }-*/;

    private static native void nativeUpdate(JavaScriptObject point, Number y, boolean redraw, boolean animation) /*-{
        point.update(y, redraw, animation);
    }-*/;

    private static native JavaScriptObject nativeGetUserData(JavaScriptObject point) /*-{
        return point.userData;
    }-*/;

}