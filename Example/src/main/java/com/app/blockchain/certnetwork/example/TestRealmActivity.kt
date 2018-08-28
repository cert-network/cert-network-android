package com.app.blockchain.certnetwork.example

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.app.blockchain.certnetwork.common.extension.hideKeyboard
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_test_realm.*

class TestRealmActivity : AppCompatActivity() {

    private var database: CustomDatabase = CustomDatabase.instance
    private var mode: Int = 0


    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomDatabase.instance.initDatabase(this)
        setContentView(R.layout.activity_test_realm)
        database
                .findAllMockObject(mode)
                .subscribe(
                        consumerFindAllObject(),
                        consumerException())


        btn_find.setOnClickListener(onClickListener())
        btn_add.setOnClickListener(onClickListener())
        btn_delete.setOnClickListener(onClickListener())
        btn_find_all.setOnClickListener(onClickListener())
//        tbtn_sync_async.setOnCheckedChangeListener(onCheckedChangeListener())
        btn_delete_all.setOnClickListener(onClickListener())
    }



    private fun onClickListener(): View.OnClickListener {
        return View.OnClickListener { v ->
            when (v) {
                btn_find -> {
                    database
                            .findMockObject(mode, edt_id.text.toString())
                            .subscribe(
                                    consumerFindObject(),
                                    consumerException())
                }
                btn_add -> {
                    database
                            .saveMockObject(mode, createMockObject())
                            .subscribe(
                                    consumerAddObject(),
                                    consumerException())
                }
                btn_delete -> {
                    database
                            .deleteMockObject(mode, edt_id.text.toString())
                            .subscribe(
                                    consumerDeleteObject(),
                                    consumerException())
                }
                btn_find_all -> {
                    database
                            .findAllMockObject(mode)
                            .subscribe(
                                    consumerFindAllObject(),
                                    consumerException())
                }
                btn_delete_all -> {
                    database
                            .deleteAllMockObject(mode)
                            .subscribe(
                                    consumerDeleteAllObject(),
                                    consumerException())
                }
            }
        }
    }

    private fun updateView() {
        database.findAllMockObject(mode)
                .subscribe(consumerFindAllObject(),
                           consumerException())
        edt_id.hideKeyboard()
    }

    private fun createMockObject(): MockObject = MockObject()

    private fun consumerFindObject(): Consumer<MockObject> {
        return Consumer { mockObject ->
            tv_message.text = mockObject.toString()
        }
    }

    private fun consumerDeleteObject(): Consumer<List<MockObject>> {
        return Consumer { updateView() }
    }

    private fun consumerAddObject(): Consumer<MockObject> {
        return Consumer { updateView() }
    }


    private fun consumerFindAllObject(): Consumer<MutableList<MockObject>> {
        return Consumer { allMockObject ->
            var str = ""
            allMockObject.forEach { str += "$it\n" }
            tv_message.text = str
        }
    }

    private fun consumerDeleteAllObject(): Consumer<MutableList<MockObject>> {
        return Consumer {
            updateView()
        }
    }

    private fun consumerException(): Consumer<Throwable> {
        return Consumer { e ->
            e.message?.let { showMessage(it) }
            edt_id.hideKeyboard()
            updateView()
        }
    }

    private fun showMessage(message: String) {
        val snackBar = Snackbar.make(findViewById(R.id.root_view), message, Snackbar.LENGTH_LONG)
        snackBar.setAction("Dismiss") { snackBar.dismiss() }
        snackBar.show()
    }
}
