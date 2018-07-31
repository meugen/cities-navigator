package meugeninua.citiesnavigator.ui.activities.base.resource

/**
 * @author meugen
 */
private const val STATUS_LOADING = 1
private const val STATUS_SUCCESS = 2
private const val STATUS_ERROR = 3

class Resource<T> private constructor(
        private val _data: T?,
        private val _status: Int,
        private val _error: Throwable?) {

    companion object {

        fun <T> loading(data: T?): Resource<T> {
            return Resource(data, STATUS_LOADING, null)
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(data, STATUS_SUCCESS, null)
        }

        fun <T> error(error: Throwable): Resource<T> {
            return Resource(null, STATUS_ERROR, error)
        }
    }

    val data: T
        get() = _data ?: throw _error ?: IllegalArgumentException("No data")
    val isLoading: Boolean
        get() = _status == STATUS_LOADING
    val isError: Boolean
        get() = _status == STATUS_ERROR
}

fun <T> Resource<T>?.toNullableData(): T? {
    if (this == null) {
        return null
    }
    try {
        return this.data
    } catch (e: Throwable) {
        return null
    }
}