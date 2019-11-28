package com.gza21.remotemusicplayer.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.adapters.FolderAdapter
import com.gza21.remotemusicplayer.managers.NetworkManager
import com.gza21.remotemusicplayer.utils.AppConstants

class FolderSelectFragment : Fragment() {

    var mContentView: View? = null
    var mAdapter: FolderAdapter? = null
    val mNwMgr = NetworkManager.instance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContentView = inflater.inflate(R.layout.fragment_select_folder, container)
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContentView?.let {
            mAdapter = FolderAdapter(context!!, object : FolderAdapter.FolderListener {
                override fun onSelect(folder: String) {
                    if (folder != AppConstants.PREVIOUS_DIR) {
                        mNwMgr.openFolder(folder)
                    } else {
                        mNwMgr.backToPrevDir()
                    }
                    mAdapter?.updateFolders()
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_select_folder, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.action_select_folder)?.setTitle("Select Folder")
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId ?: 0 == R.id.action_select_folder) {
            selectFolder()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectFolder() {

    }

}