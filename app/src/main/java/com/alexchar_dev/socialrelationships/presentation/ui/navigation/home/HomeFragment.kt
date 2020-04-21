package com.alexchar_dev.socialrelationships.presentation.ui.navigation.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.utils.OnSwipeTouchListener
import com.alexchar_dev.socialrelationships.presentation.utils.hide
import com.alexchar_dev.socialrelationships.presentation.utils.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.function.BinaryOperator


class HomeFragment : Fragment() {
    private val viewModel : HomeViewModel by viewModel()
    private var adapter: FriendFirestoreRecyclerAdapter? = null
    private var sliderOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        addSomeFriendsBtn.setOnClickListener {
            activity?.bottomNavigationView?.selectedItemId = R.id.search_nav //TODO probably bad idea to access activity view directly

        }
        adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if(adapter!!.itemCount > 0) group.hide() else group.show()
            }
        })

        setSliderAnimation()
    }

    private fun setSliderAnimation() {

        slider_btn.setOnClickListener {
            sliderOpen = toggleSettingsSlider(sliderOpen)
        }

        home_constraint.setOnTouchListener(onSwipeTouchListener())
        friends_recyclerview.setOnTouchListener(onSwipeTouchListener())
    }

    private fun onSwipeTouchListener(): OnSwipeTouchListener {
        return object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                if (sliderOpen) sliderOpen = toggleSettingsSlider(sliderOpen)
            }

            override fun onSwipeLeft() {
                if (!sliderOpen) sliderOpen = toggleSettingsSlider(sliderOpen)
            }
        }
    }

    private fun toggleSettingsSlider(sliderOpen: Boolean) : Boolean {
        val width = resources.getDimensionPixelSize(R.dimen.sliderWidth).toFloat()
        if (sliderOpen) {
            ObjectAnimator.ofFloat(home_constraint,"translationX", 0f).apply{
                duration = 200
                start()
            }
            ObjectAnimator.ofFloat(slider,"translationX", 0f).apply{
                duration = 200
                start()
            }
        } else {
            ObjectAnimator.ofFloat(home_constraint,"translationX", -width).apply{
                duration = 200
                start()
            }
            ObjectAnimator.ofFloat(slider,"translationX", -width).apply{
                duration = 200
                start()
            }
        }
        return !sliderOpen
    }

    private fun setUpRecyclerView() {
        friends_recyclerview.layoutManager = LinearLayoutManager(context)
        val friends = viewModel.getFriends()
        adapter = FriendFirestoreRecyclerAdapter(friends)
        friends_recyclerview.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            friends_recyclerview.context,
            LinearLayoutManager.VERTICAL
        )
        friends_recyclerview.addItemDecoration(dividerItemDecoration)
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    //TODO override onSaveInstanceState and save if the sliderOpen is true
}
