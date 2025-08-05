package com.openlewa.remotebar.util

import android.content.Context
import android.view.Surface
import android.view.WindowManager
import androidx.test.core.app.ApplicationProvider
import java.util.function.Predicate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class RemotebarPositionTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        Assert.assertNotNull(context)
    }

    @Test
    fun testIsVertical() {
        Assert.assertFalse(RemotebarPosition.isVertical(Constants.POSITION_BOTTOM_LEFT))
        Assert.assertFalse(RemotebarPosition.isVertical(Constants.POSITION_BOTTOM_RIGHT))
        Assert.assertTrue(RemotebarPosition.isVertical(Constants.POSITION_BOTTOM_VERTICAL_LEFT))
        Assert.assertTrue(RemotebarPosition.isVertical(Constants.POSITION_BOTTOM_VERTICAL_RIGHT))
        Assert.assertFalse(RemotebarPosition.isVertical(Constants.POSITION_TOP_LEFT))
        Assert.assertFalse(RemotebarPosition.isVertical(Constants.POSITION_TOP_RIGHT))
        Assert.assertTrue(RemotebarPosition.isVertical(Constants.POSITION_TOP_VERTICAL_LEFT))
        Assert.assertTrue(RemotebarPosition.isVertical(Constants.POSITION_TOP_VERTICAL_RIGHT))
    }

    @Test
    fun testIsVerticalWithContext() {
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_LEFT,
                { context: Context? -> RemotebarPosition.isVertical(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_RIGHT,
                { context: Context? -> RemotebarPosition.isVertical(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isVertical(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isVertical(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_LEFT,
                { context: Context? -> RemotebarPosition.isVertical(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_RIGHT,
                { context: Context? -> RemotebarPosition.isVertical(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isVertical(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isVertical(context) },
                true
        )
    }

    @Test
    fun testIsLeft() {
        Assert.assertTrue(RemotebarPosition.isLeft(Constants.POSITION_BOTTOM_LEFT))
        Assert.assertFalse(RemotebarPosition.isLeft(Constants.POSITION_BOTTOM_RIGHT))
        Assert.assertTrue(RemotebarPosition.isLeft(Constants.POSITION_BOTTOM_VERTICAL_LEFT))
        Assert.assertFalse(RemotebarPosition.isLeft(Constants.POSITION_BOTTOM_VERTICAL_RIGHT))
        Assert.assertTrue(RemotebarPosition.isLeft(Constants.POSITION_TOP_LEFT))
        Assert.assertFalse(RemotebarPosition.isLeft(Constants.POSITION_TOP_RIGHT))
        Assert.assertTrue(RemotebarPosition.isLeft(Constants.POSITION_TOP_VERTICAL_LEFT))
        Assert.assertFalse(RemotebarPosition.isLeft(Constants.POSITION_TOP_VERTICAL_RIGHT))
    }

    @Test
    fun testIsLeftWithContext() {
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_LEFT,
                { context: Context? -> RemotebarPosition.isLeft(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_RIGHT,
                { context: Context? -> RemotebarPosition.isLeft(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isLeft(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isLeft(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_LEFT,
                { context: Context? -> RemotebarPosition.isLeft(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_RIGHT,
                { context: Context? -> RemotebarPosition.isLeft(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isLeft(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isLeft(context) },
                false
        )
    }

    @Test
    fun testIsRight() {
        Assert.assertFalse(RemotebarPosition.isRight(Constants.POSITION_BOTTOM_LEFT))
        Assert.assertTrue(RemotebarPosition.isRight(Constants.POSITION_BOTTOM_RIGHT))
        Assert.assertFalse(RemotebarPosition.isRight(Constants.POSITION_BOTTOM_VERTICAL_LEFT))
        Assert.assertTrue(RemotebarPosition.isRight(Constants.POSITION_BOTTOM_VERTICAL_RIGHT))
        Assert.assertFalse(RemotebarPosition.isRight(Constants.POSITION_TOP_LEFT))
        Assert.assertTrue(RemotebarPosition.isRight(Constants.POSITION_TOP_RIGHT))
        Assert.assertFalse(RemotebarPosition.isRight(Constants.POSITION_TOP_VERTICAL_LEFT))
        Assert.assertTrue(RemotebarPosition.isRight(Constants.POSITION_TOP_VERTICAL_RIGHT))
    }

    @Test
    fun testIsRightWithContext() {
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_LEFT,
                { context: Context? -> RemotebarPosition.isRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_RIGHT,
                { context: Context? -> RemotebarPosition.isRight(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isRight(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_LEFT,
                { context: Context? -> RemotebarPosition.isRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_RIGHT,
                { context: Context? -> RemotebarPosition.isRight(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isRight(context) },
                true
        )
    }

    @Test
    fun testIsBottom() {
        Assert.assertTrue(RemotebarPosition.isBottom(Constants.POSITION_BOTTOM_LEFT))
        Assert.assertTrue(RemotebarPosition.isBottom(Constants.POSITION_BOTTOM_RIGHT))
        Assert.assertTrue(RemotebarPosition.isBottom(Constants.POSITION_BOTTOM_VERTICAL_LEFT))
        Assert.assertTrue(RemotebarPosition.isBottom(Constants.POSITION_BOTTOM_VERTICAL_RIGHT))
        Assert.assertFalse(RemotebarPosition.isBottom(Constants.POSITION_TOP_LEFT))
        Assert.assertFalse(RemotebarPosition.isBottom(Constants.POSITION_TOP_RIGHT))
        Assert.assertFalse(RemotebarPosition.isBottom(Constants.POSITION_TOP_VERTICAL_LEFT))
        Assert.assertFalse(RemotebarPosition.isBottom(Constants.POSITION_TOP_VERTICAL_RIGHT))
    }

    @Test
    fun testIsBottomWithContext() {
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_LEFT,
                { context: Context? -> RemotebarPosition.isBottom(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_RIGHT,
                { context: Context? -> RemotebarPosition.isBottom(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isBottom(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isBottom(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_LEFT,
                { context: Context? -> RemotebarPosition.isBottom(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_RIGHT,
                { context: Context? -> RemotebarPosition.isBottom(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isBottom(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isBottom(context) },
                false
        )
    }

    @Test
    fun testIsVerticalLeft() {
        Assert.assertFalse(RemotebarPosition.isVerticalLeft(Constants.POSITION_BOTTOM_LEFT))
        Assert.assertFalse(RemotebarPosition.isVerticalLeft(Constants.POSITION_BOTTOM_RIGHT))
        Assert.assertTrue(RemotebarPosition.isVerticalLeft(Constants.POSITION_BOTTOM_VERTICAL_LEFT))
        Assert.assertFalse(RemotebarPosition.isVerticalLeft(Constants.POSITION_BOTTOM_VERTICAL_RIGHT))
        Assert.assertFalse(RemotebarPosition.isVerticalLeft(Constants.POSITION_TOP_LEFT))
        Assert.assertFalse(RemotebarPosition.isVerticalLeft(Constants.POSITION_TOP_RIGHT))
        Assert.assertTrue(RemotebarPosition.isVerticalLeft(Constants.POSITION_TOP_VERTICAL_LEFT))
        Assert.assertFalse(RemotebarPosition.isVerticalLeft(Constants.POSITION_TOP_VERTICAL_RIGHT))
    }

    @Test
    fun testIsVerticalLeftWithContext() {
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_LEFT,
                { context: Context? -> RemotebarPosition.isVerticalLeft(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_RIGHT,
                { context: Context? -> RemotebarPosition.isVerticalLeft(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isVerticalLeft(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isVerticalLeft(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_LEFT,
                { context: Context? -> RemotebarPosition.isVerticalLeft(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_RIGHT,
                { context: Context? -> RemotebarPosition.isVerticalLeft(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isVerticalLeft(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isVerticalLeft(context) },
                false
        )
    }

    @Test
    fun testIsVerticalRight() {
        Assert.assertFalse(RemotebarPosition.isVerticalRight(Constants.POSITION_BOTTOM_LEFT))
        Assert.assertFalse(RemotebarPosition.isVerticalRight(Constants.POSITION_BOTTOM_RIGHT))
        Assert.assertFalse(RemotebarPosition.isVerticalRight(Constants.POSITION_BOTTOM_VERTICAL_LEFT))
        Assert.assertTrue(RemotebarPosition.isVerticalRight(Constants.POSITION_BOTTOM_VERTICAL_RIGHT))
        Assert.assertFalse(RemotebarPosition.isVerticalRight(Constants.POSITION_TOP_LEFT))
        Assert.assertFalse(RemotebarPosition.isVerticalRight(Constants.POSITION_TOP_RIGHT))
        Assert.assertFalse(RemotebarPosition.isVerticalRight(Constants.POSITION_TOP_VERTICAL_LEFT))
        Assert.assertTrue(RemotebarPosition.isVerticalRight(Constants.POSITION_TOP_VERTICAL_RIGHT))
    }

    @Test
    fun testIsVerticalRightWithContext() {
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_LEFT,
                { context: Context? -> RemotebarPosition.isVerticalRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_RIGHT,
                { context: Context? -> RemotebarPosition.isVerticalRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isVerticalRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_BOTTOM_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isVerticalRight(context) },
                true
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_LEFT,
                { context: Context? -> RemotebarPosition.isVerticalRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_RIGHT,
                { context: Context? -> RemotebarPosition.isVerticalRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_LEFT,
                { context: Context? -> RemotebarPosition.isVerticalRight(context) },
                false
        )
        checkRemotebarPositionGroup(
                Constants.POSITION_TOP_VERTICAL_RIGHT,
                { context: Context? -> RemotebarPosition.isVerticalRight(context) },
                true
        )
    }

    @Test
    fun testGetRemotebarPositionWithoutAnchor() {
        val position = RemotebarPosition.getRemotebarPosition(context)
        // The default position is bottom_left
        Assert.assertEquals(Constants.POSITION_BOTTOM_LEFT, position)
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndPositionBottomLeft() {
        checkRemotebarPositionWithDifferentRotation(
                Constants.POSITION_BOTTOM_LEFT,
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_BOTTOM_LEFT)
                        add(Constants.POSITION_BOTTOM_VERTICAL_RIGHT)
                        add(Constants.POSITION_TOP_RIGHT)
                        add(Constants.POSITION_TOP_VERTICAL_LEFT)
                    }
                }
        )
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndPositionBottomVerticalLeft() {
        checkRemotebarPositionWithDifferentRotation(
                Constants.POSITION_BOTTOM_VERTICAL_LEFT,
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_BOTTOM_VERTICAL_LEFT)
                        add(Constants.POSITION_BOTTOM_RIGHT)
                        add(Constants.POSITION_TOP_VERTICAL_RIGHT)
                        add(Constants.POSITION_TOP_LEFT)
                    }
                }
        )
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndPositionBottomRight() {
        checkRemotebarPositionWithDifferentRotation(
                Constants.POSITION_BOTTOM_RIGHT,
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_BOTTOM_RIGHT)
                        add(Constants.POSITION_TOP_VERTICAL_RIGHT)
                        add(Constants.POSITION_TOP_LEFT)
                        add(Constants.POSITION_BOTTOM_VERTICAL_LEFT)
                    }
                }
        )
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndPositionBottomVerticalRight() {
        checkRemotebarPositionWithDifferentRotation(
                Constants.POSITION_BOTTOM_VERTICAL_RIGHT,
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_BOTTOM_VERTICAL_RIGHT)
                        add(Constants.POSITION_TOP_RIGHT)
                        add(Constants.POSITION_TOP_VERTICAL_LEFT)
                        add(Constants.POSITION_BOTTOM_LEFT)
                    }
                }
        )
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndPositionTopLeft() {
        checkRemotebarPositionWithDifferentRotation(
                Constants.POSITION_TOP_LEFT,
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_TOP_LEFT)
                        add(Constants.POSITION_BOTTOM_VERTICAL_LEFT)
                        add(Constants.POSITION_BOTTOM_RIGHT)
                        add(Constants.POSITION_TOP_VERTICAL_RIGHT)
                    }
                }
        )
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndPositionTopVerticalLeft() {
        checkRemotebarPositionWithDifferentRotation(
                Constants.POSITION_TOP_VERTICAL_LEFT,
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_TOP_VERTICAL_LEFT)
                        add(Constants.POSITION_BOTTOM_LEFT)
                        add(Constants.POSITION_BOTTOM_VERTICAL_RIGHT)
                        add(Constants.POSITION_TOP_RIGHT)
                    }
                }
        )
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndPositionTopRight() {
        checkRemotebarPositionWithDifferentRotation(
                Constants.POSITION_TOP_RIGHT,
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_TOP_RIGHT)
                        add(Constants.POSITION_TOP_VERTICAL_LEFT)
                        add(Constants.POSITION_BOTTOM_LEFT)
                        add(Constants.POSITION_BOTTOM_VERTICAL_RIGHT)
                    }
                }
        )
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndPositionTopVerticalRight() {
        checkRemotebarPositionWithDifferentRotation(
                Constants.POSITION_TOP_VERTICAL_RIGHT,
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_TOP_VERTICAL_RIGHT)
                        add(Constants.POSITION_TOP_LEFT)
                        add(Constants.POSITION_BOTTOM_VERTICAL_LEFT)
                        add(Constants.POSITION_BOTTOM_RIGHT)
                    }
                }
        )
    }

    @Test
    fun testGetRemotebarPositionWithAnchorAndInvalidPosition() {
        checkRemotebarPositionWithDifferentRotation(
                "invalid-position",
                object : ArrayList<String>() {
                    init {
                        add(Constants.POSITION_BOTTOM_LEFT)
                        add(Constants.POSITION_BOTTOM_LEFT)
                        add(Constants.POSITION_BOTTOM_LEFT)
                        add(Constants.POSITION_BOTTOM_LEFT)
                    }
                }
        )
    }

    private fun checkRemotebarPositionWithDifferentRotation(
        originPosition: String,
        changedPositions: List<String>
    ) {
        Assert.assertEquals(4, changedPositions.size.toLong())
        val oldPosition = U.getSharedPreferences(context)
                .getString(Constants.PREF_POSITION, Constants.POSITION_BOTTOM_LEFT)
        val oldAnchor = U.getSharedPreferences(context)
                .getBoolean(Constants.PREF_ANCHOR, false)
        initializeRemotebarPosition(originPosition)
        initializeRotation(Surface.ROTATION_0)
        Assert.assertEquals(changedPositions[0], RemotebarPosition.getRemotebarPosition(context))
        initializeRotation(Surface.ROTATION_90)
        Assert.assertEquals(changedPositions[1], RemotebarPosition.getRemotebarPosition(context))
        initializeRotation(Surface.ROTATION_180)
        Assert.assertEquals(changedPositions[2], RemotebarPosition.getRemotebarPosition(context))
        initializeRotation(Surface.ROTATION_270)
        Assert.assertEquals(changedPositions[3], RemotebarPosition.getRemotebarPosition(context))
        U.getSharedPreferences(context).edit()
                .putBoolean(Constants.PREF_ANCHOR, oldAnchor).apply()
        U.getSharedPreferences(context).edit()
                .putString(Constants.PREF_POSITION, oldPosition).apply()
    }

    private fun checkRemotebarPositionGroup(
        originPosition: String,
        predicate: Predicate<Context?>,
        expectedResult: Boolean
    ) {
        val oldPosition = U.getSharedPreferences(context)
                .getString(Constants.PREF_POSITION, Constants.POSITION_BOTTOM_LEFT)
        val oldAnchor = U.getSharedPreferences(context)
                .getBoolean(Constants.PREF_ANCHOR, false)
        initializeRemotebarPosition(originPosition)
        Assert.assertEquals(expectedResult, predicate.test(context))
        U.getSharedPreferences(context).edit()
                .putBoolean(Constants.PREF_ANCHOR, oldAnchor).apply()
        U.getSharedPreferences(context).edit()
                .putString(Constants.PREF_POSITION, oldPosition).apply()
    }

    private fun initializeRemotebarPosition(position: String) {
        U.getSharedPreferences(context).edit()
                .putBoolean(Constants.PREF_ANCHOR, true).apply()
        U.getSharedPreferences(context).edit()
                .putString(Constants.PREF_POSITION, position).apply()
    }

    private fun initializeRotation(rotation: Int) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val shadowDisplay = Shadows.shadowOf(display)
        shadowDisplay.setRotation(rotation)
    }
}
