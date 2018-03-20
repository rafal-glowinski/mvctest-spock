package com.rg.webmvctest.rest.util

import java.io.PrintWriter
import java.io.StringWriter
import java.util.Objects

/**
 * This is a set of classes for REST API error handling. It has been copied from another project to provide
 * a standard way to report various types of errors.
 */

class ErrorsHolder(val errors: List<Error>)

class Error(
        /**
         * @return error code, this (and path if present) must uniquely identify error
         */
        val code: String? = null,
        /**
         * @return message for technical interpretation (i.e. message code)
         */
        val message: String? = null,
        /**
         * @return additional details, might contain stack trace
         */
        val details: String? = null,
        /**
         * @return path to field that contains error
         */
        val path: String? = null,
        /**
         * @return user friendly translated message
         */
        val userMessage: String? = null
) {

    fun cloneWithPrefixedPath(prefix: String): Error {
        return Error.like(this).addPrefixToPath(prefix).build()
    }

    override fun equals(obj: Any?): Boolean {
        if (obj !is Error) {
            return false
        }

        val other = obj as Error?
        return (code == other!!.code
                && path == other.path
                && message == other.message
                && details == other.details
                && userMessage == other.userMessage)
    }

    override fun hashCode(): Int {
        return Objects.hash(code, path, message, details, userMessage)
    }

    override fun toString(): String {
        return String.format("Error{code='%s', message='%s', details='%s', path='%s', userMessage='%s'}", code, message, details, path, userMessage)
    }

    class Builder {

        private var code: String? = null

        private var message: String? = null

        private var details: String? = null

        private var path: String? = null

        private var userMessage: String? = null

        fun build(): Error {
            return Error(code, message, details, path, userMessage)
        }

        fun fromException(exception: Exception): Builder {
            return fromException(exception, false)
        }

        fun fromException(exception: Throwable, withStackTrace: Boolean): Builder {
            this.code = exception.javaClass.simpleName
            this.message = exception.message
            if (withStackTrace) {
                this.details = StackTraceDumper.dumpStackTrace(exception)
            }
            return this
        }

        fun withCode(code: String?): Builder {
            this.code = code
            return this
        }

        fun withMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun withDetails(details: String?): Builder {
            this.details = details
            return this
        }

        fun withUserMessage(userMessage: String?): Builder {
            this.userMessage = userMessage
            return this
        }

        fun withPath(path: String?): Builder {
            this.path = path
            return this
        }

        fun addPrefixToPath(prefix: String): Builder {
            if (this.path != null) {
                this.path = StringBuilder(prefix).append(Error.PATH_SEPARATOR).append(this.path).toString()
            } else {
                this.path = prefix
            }
            return this
        }
    }

    companion object {

        const val PATH_SEPARATOR = '.'

        fun error(): Builder {
            return Builder()
        }

        fun error(code: String): Builder {
            return Builder().withCode(code)
        }

        fun like(other: Error): Builder {
            return Builder()
                    .withCode(other.code)
                    .withMessage(other.message)
                    .withDetails(other.details)
                    .withPath(other.path)
                    .withUserMessage(other.userMessage)
        }
    }
}

internal object StackTraceDumper {

    fun dumpStackTrace(exception: Throwable): String {
        val stringWriter = StringWriter()
        exception.printStackTrace(PrintWriter(stringWriter))

        return stringWriter.toString()
    }
}