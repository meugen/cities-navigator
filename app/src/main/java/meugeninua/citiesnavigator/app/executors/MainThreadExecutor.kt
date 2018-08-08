package meugeninua.citiesnavigator.app.executors

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * @author meugen
 */
class MainThreadExecutor: Executor {

    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun execute(r: Runnable?) {
        if (r != null) {
            handler.post(r)
        }
    }
}