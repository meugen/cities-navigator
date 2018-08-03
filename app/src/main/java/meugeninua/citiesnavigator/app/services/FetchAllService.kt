package meugeninua.citiesnavigator.app.services

import android.os.Bundle
import com.firebase.jobdispatcher.*
import kotlinx.coroutines.experimental.CancellationException
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import meugeninua.citiesnavigator.model.db.CitiesDao
import meugeninua.citiesnavigator.model.repositories.MainRepository
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * @author meugen
 */
class FetchAllService: JobService() {

    private val repository: MainRepository by inject()
    private val citiesDao: CitiesDao by inject()

    private val jobs = mutableMapOf<String, Deferred<*>>()

    override fun onStopJob(job: JobParameters?): Boolean {
        val tag = job?.tag ?: return false
        jobs.remove(tag)?.cancel()
        return true
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        val tag = job?.tag ?: return false
        Timber.d("Job is started")

        jobs[tag] = async(onCompletion = { onCompleted(job, it) }) { fetchAll() }
        return true
    }

    private fun onCompleted(job: JobParameters, e: Throwable?) {
        jobs.remove(job.tag)
        if (e != null) {
            Timber.d(e)
        }
        jobFinished(job, e != null && e !is CancellationException)
    }

    private suspend fun fetchAll() {
        if (citiesDao.citiesCount() == 0) {
            val cities = repository.cities()
            citiesDao.insertCities(cities)
        }
        if (citiesDao.countriesCount() == 0) {
            val countries = repository.countries()
            citiesDao.insertCountries(countries)
        }
    }

    class Launcher {

        fun launch(dispatcher: FirebaseJobDispatcher) {
            val job = dispatcher.newJobBuilder()
                    .setExtras(Bundle.EMPTY)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                    .setRecurring(false)
                    .setReplaceCurrent(true)
                    .setService(FetchAllService::class.java)
                    .setTag("fetch-all-cities-and-countries")
                    .setTrigger(Trigger.NOW)
                    .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                    .build()
            dispatcher.mustSchedule(job)
        }
    }
}