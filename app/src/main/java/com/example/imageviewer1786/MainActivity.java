package com.example.imageviewer1786;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// Main activity class that extends AppCompatActivity and implements the OnClickListener for button clicks
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // UI elements declaration
    private ImageView imageView;
    private Button backButton;
    private Button forwardButton;
    private TextView imageNumberTextView;
    // List to hold the resource IDs of the images
    private List<Integer> imageList;
    // Index to track the current image displayed
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to our XML layout
        setContentView(R.layout.activity_main);

        // Initialize the UI elements
        imageView = findViewById(R.id.imageView);
        backButton = findViewById(R.id.backButton);
        forwardButton = findViewById(R.id.forwardButton);
        imageNumberTextView = findViewById(R.id.imageNumberTextView);

        // Set click listeners for the buttons
        backButton.setOnClickListener(this);
        forwardButton.setOnClickListener(this);

        // Populate the list of images from resources
        imageList = getImageResourceList();

        // Set the initial state of the image view and text
        updateImageView();
        updateImageNumberText();
        // Set the initial state of the buttons (enabled/disabled)
        updateButtonStates();
    }

    // Click event handling for the navigation buttons
    @Override
    public void onClick(View v) {
        // Determine which button was clicked
        if (v.getId() == R.id.backButton) {
            navigateBackward();
        } else if (v.getId() == R.id.forwardButton) {
            navigateForward();
        }
    }

    // Navigate to the previous image in the list
    private void navigateBackward() {
        if (currentImageIndex > 0) {
            currentImageIndex--;
            // Update the image view and text after navigation
            updateImageView();
            updateImageNumberText();
        }
        // Update button states based on the new index
        updateButtonStates();
    }

    // Navigate to the next image in the list
    private void navigateForward() {
        if (currentImageIndex < imageList.size() - 1) {
            currentImageIndex++;
            // Update the image view and text after navigation
            updateImageView();
            updateImageNumberText();
        }
        // Update button states based on the new index
        updateButtonStates();
    }

    // Update the enabled/disabled state of the navigation buttons
    private void updateButtonStates() {
        // Enable or disable buttons based on the current index
        backButton.setEnabled(currentImageIndex > 0);
        forwardButton.setEnabled(currentImageIndex < imageList.size() - 1);
        // Change the opacity of buttons for a visual disabled effect
        backButton.setAlpha(currentImageIndex > 0 ? 1.0f : 0.5f);
        forwardButton.setAlpha(currentImageIndex < imageList.size() - 1 ? 1.0f : 0.5f);
    }

    // Update the ImageView to show the current image
    private void updateImageView() {
        int imageResource = imageList.get(currentImageIndex);
        imageView.setImageResource(imageResource);
    }

    // Update the TextView to show the current image index
    private void updateImageNumberText() {
        String imageNumberText = (currentImageIndex + 1) + "/" + imageList.size();
        imageNumberTextView.setText(imageNumberText);
    }

    // Retrieve and return a list of all image resources
    private List<Integer> getImageResourceList() {
        List<Integer> resourceList = new ArrayList<>();
        // Use reflection to iterate over all drawable resources
        Field[] drawablesFields = R.drawable.class.getFields();
        for (Field field : drawablesFields) {
            try {
                // Check for fields that start with 'image' and add them to the list
                String resourceName = field.getName();
                if (resourceName.startsWith("image")) {
                    int resourceId = field.getInt(null);
                    resourceList.add(resourceId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resourceList;
    }

}
