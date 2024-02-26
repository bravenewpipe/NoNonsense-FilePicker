/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.nononsenseapps.filepicker.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nononsenseapps.filepicker.AbstractFilePickerFragment;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;
import com.nononsenseapps.filepicker.sample.databinding.ActivityNoNonsenseFilePickerBinding;
import com.nononsenseapps.filepicker.sample.dropbox.DropboxFilePickerActivity;
import com.nononsenseapps.filepicker.sample.dropbox.DropboxFilePickerActivity2;
import com.nononsenseapps.filepicker.sample.fastscroller.FastScrollerFilePickerActivity;
import com.nononsenseapps.filepicker.sample.fastscroller.FastScrollerFilePickerActivity2;
import com.nononsenseapps.filepicker.sample.ftp.FtpPickerActivity;
import com.nononsenseapps.filepicker.sample.ftp.FtpPickerActivity2;
import com.nononsenseapps.filepicker.sample.multimedia.MultimediaPickerActivity;
import com.nononsenseapps.filepicker.sample.multimedia.MultimediaPickerActivity2;
import com.nononsenseapps.filepicker.sample.root.SUPickerActivity;
import com.nononsenseapps.filepicker.sample.root.SUPickerActivity2;

import java.util.List;


public class NoNonsenseFilePicker extends AppCompatActivity {

    // How to handle multiple return data
    public static boolean useClipData = true;

    ActivityNoNonsenseFilePickerBinding binding;
    private final ActivityResultLauncher<Intent> codeDbOrFtpLauncher =
            registerForActivityResult(new StartActivityForResult(), this::codeDbOrFtpResult);
    private final ActivityResultLauncher<Intent> codeSdLauncher =
            registerForActivityResult(new StartActivityForResult(), this::codeSdResult);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_nonsense_file_picker);

        binding.buttonSd
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (binding.checkLightTheme.isChecked()) {
                            startActivity(codeSdLauncher, FilePickerActivity2.class);
                        } else {
                            startActivity(codeSdLauncher, FilePickerActivity.class);
                        }
                    }
                });

        binding.buttonImage
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (binding.checkLightTheme.isChecked()) {
                            startActivity(codeSdLauncher, MultimediaPickerActivity2.class);
                        } else {
                            startActivity(codeSdLauncher, MultimediaPickerActivity.class);
                        }
                    }
                });

        binding.buttonFtp
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (binding.checkLightTheme.isChecked()) {
                            startActivity(codeDbOrFtpLauncher, FtpPickerActivity2.class);
                        } else {
                            startActivity(codeDbOrFtpLauncher, FtpPickerActivity.class);
                        }
                    }
                });

        binding.buttonDropbox
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (binding.checkLightTheme.isChecked()) {
                            startActivity(codeDbOrFtpLauncher, DropboxFilePickerActivity2.class);
                        } else {
                            startActivity(codeDbOrFtpLauncher, DropboxFilePickerActivity.class);
                        }
                    }
                });

        binding.buttonRoot
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (binding.checkLightTheme.isChecked()) {
                            startActivity(codeSdLauncher, SUPickerActivity.class);
                        } else {
                            startActivity(codeSdLauncher, SUPickerActivity2.class);
                        }
                    }
                });

        binding.buttonFastscroll
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (binding.checkLightTheme.isChecked()) {
                            startActivity(codeSdLauncher, FastScrollerFilePickerActivity.class);
                        } else {
                            startActivity(codeSdLauncher, FastScrollerFilePickerActivity2.class);
                        }
                    }
                });
    }

    protected void startActivity(final ActivityResultLauncher<Intent> launcher, final Class<?> klass) {
        final Intent i = new Intent(this, klass);

        i.setAction(Intent.ACTION_GET_CONTENT);

        i.putExtra(SUPickerActivity.EXTRA_ALLOW_MULTIPLE, binding.checkAllowMultiple.isChecked());
        i.putExtra(FilePickerActivity.EXTRA_SINGLE_CLICK, binding.checkSingleClick.isChecked());
        i.putExtra(SUPickerActivity.EXTRA_ALLOW_CREATE_DIR, binding.checkAllowCreateDir.isChecked());
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_EXISTING_FILE, binding.checkAllowExistingFile.isChecked());

        // What mode is selected
        final int mode;
        switch (binding.radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioDir:
                mode = AbstractFilePickerFragment.MODE_DIR;
                break;
            case R.id.radioFilesAndDirs:
                mode = AbstractFilePickerFragment.MODE_FILE_AND_DIR;
                break;
            case R.id.radioNewFile:
                mode = AbstractFilePickerFragment.MODE_NEW_FILE;
                break;
            case R.id.radioFile:
            default:
                mode = AbstractFilePickerFragment.MODE_FILE;
                break;
        }

        i.putExtra(FilePickerActivity.EXTRA_MODE, mode);

        // This line is solely so that test classes can override intents given through UI
        i.putExtras(getIntent());

        launcher.launch(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.no_nonsense_file_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void codeDbOrFtpResult(final ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            // Use the provided utility method to parse the result
            final List<Uri> files = Utils.getSelectedFilesFromResult(result.getData());

            // Do something with your list of files here
            final StringBuilder sb = new StringBuilder();
            for (final Uri uri : files) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(uri.toString());
            }
            binding.text.setText(sb.toString());
        }
    }

    private void codeSdResult(final ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            // Use the provided utility method to parse the result
            final List<Uri> files = Utils.getSelectedFilesFromResult(result.getData());

            // Do something with your list of files here
            final StringBuilder sb = new StringBuilder();
            for (final Uri uri : files) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(Utils.getFileForUri(uri));
            }
            binding.text.setText(sb.toString());
        }
    }
}
