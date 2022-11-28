/*
 * Copyright (c) 2021 The Android Open Source Project
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

package com.example.android.sports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.android.sports.databinding.FragmentSportsListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class SportsListFragment : Fragment() {

    private val sportsViewModel: SportsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSportsListBinding.inflate(inflater, container, false).root
    }

    // In the SportsListFragment file, in the function onViewCreated(), locate the following lines,
    // which navigate to the details screen.
    // val action = SportsListFragmentDirections.actionSportsListFragmentToNewsFragment()
    //this.findNavController().navigate(action)

    // Replace the above lines with the following code: binding.slidingPaneLayout.openPane()
    // Call openPane() on the SlidingPaneLayout to swap the second pane over the first pane. This
    // will not have any visible effect if both panes are visible such as on a tablet.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSportsListBinding.bind(view)
        // 1. In the SportsListFragment file, inside the function onViewCreated(), just below the
        // declaration of the binding variable, create an instance for the SlidingPaneLayout and
        // assign the value of binding.slidingPaneLayout to it.
        val slidingPaneLayout = binding.slidingPaneLayout
        // 2. In the SportsListFragment file, inside the function onViewCreated(), just below the
        // declaration of the slidingPaneLayout, add the following code:
        // Connect the SlidingPaneLayout to the system back button.

        //  It's a good idea to lock the SlidingPaneLayout in order to prevent users from swiping
        //  in and out using gestures. To implement this, in the onViewCreated() method, below the
        //  slidingPaneLayout definition, set the lockMode to LOCK_MODE_LOCKED:
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            SportsListOnBackPressedCallback(slidingPaneLayout)
        )


        // Initialize the adapter and set it to the RecyclerView.
        val adapter = SportsAdapter {
            // Update the user selected sport as the current sport in the shared viewmodel
            // This will automatically update the dual pane content
            sportsViewModel.updateCurrentSport(it)
            // Navigate to the details screen
            binding.slidingPaneLayout.openPane()
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(sportsViewModel.sportsData)
    }
}

// First, define the custom callback.
//
//    1. In the SportsListFragment file, add a new class below the SportsListFragment class definition.
//    Name it SportsListOnBackPressedCallback.

//    2. Pass in a private instance of SlidingPaneLayout as a constructor parameter.

//  8. The final step in completing the callback is to add the SportsListOnBackPressedCallback
//  listener class to the list of listeners that will be notified of the details pane slide events.
//  Add an init block to the SportsListOnBackPressedCallback class. Inside the init block, make a
//  call to slidingPaneLayout.addPanelSlideListener() passing in this.
class SportsListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout


    // 3. Extend the class from OnBackPressedCallback. The OnBackPressedCallback class handles
    // onBackPressed callbacks. You will fix the constructor parameter error shortly.

    // 4. Pass in slidingPaneLayout.isSlideable* && slidingPaneLayout.isOpen* as constructor
    // parameter to OnBackPressedCallback. The boolean isSlideable will only be true if the second
    // pane is slidable, which would be on a smaller screen and a single pane is being displayed.
    // The value of isOpen will be true if the second pane - the contents pane is completely open.

    // 8. (Register the callback 1.) Extend the SportsListOnBackPressedCallback class from
    // SlidingPaneLayout.PanelSlideListener.

    // 2. To resolve the error, implement the three methods. Click on the red bulb and select
    // Implement members in the Android Studio. (Highlight the 3 members before clicking okay)

): OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    // 5. To fix the error about the unimplemented method, click on the red bulb  and select Implement member
    // 6. Click ok in the Implement members popup to override the handleOnBackPressed method.
    override fun handleOnBackPressed() {
        // 7. Inside the handleOnBackPressed() function, delete the TO DO statement and add the
        // following code to close the content pane and return to the list pane. slidingPaneLayout.closePane()
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {

    }

    // Enable the OnBackPressedCallback callback, when the details pane is opened (is visible) .
    // This can be achieved by making a call to setEnabled() function and passing in true. Write
    // the following code inside onPanelOpened(): setEnabled(true) or isEnabled = true
    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }
// Similarly set isEnabled to false, when the details pane is closed.
    override fun onPanelClosed(panel: View) {
    isEnabled = false
}
}


