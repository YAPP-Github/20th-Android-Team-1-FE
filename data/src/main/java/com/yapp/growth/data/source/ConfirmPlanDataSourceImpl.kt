package com.yapp.growth.data.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.utils.toDay
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTableUnit
import com.yapp.growth.domain.entity.ResponsePlan
import com.yapp.growth.domain.entity.TimeTableDate
import com.yapp.growth.domain.entity.User
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

internal class ConfirmPlanDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi
) : ConfirmPlanDataSource {

    private val parseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
    private val hourFormat = SimpleDateFormat("HH:mm", Locale.KOREA)

    override suspend fun getRespondUsers(promisingKey: Long): NetworkResult<ResponsePlan> {
        val list = listOf<ResponsePlan>()
        val user1 = User(2313644686, "민수빈")
        val user2 = User(2317332548, "문선영")
        val user3 = User(2317331838, "한지희")
        val user4 = User(2295332548, "김정호")

        val minTime = "2022-07-09T13:00:00"
        val maxTime = "2022-07-11T16:00:00"

        val users = listOf(user1.copy(), user2.copy(), user3.copy(), user4.copy())

        val colors = listOf(442921462, 1298559478)

        val avaliableDate = listOf(
            "2022-07-09T09:00:00".toDay(),
            "2022-07-10T09:00:00".toDay(),
            "2022-07-11T09:00:00".toDay(),
            "2022-07-12T09:00:00".toDay(),
//            "2022-07-13 09:00:00".toDay(),
//            "2022-07-14 09:00:00".toDay(),
//            "2022-07-15 09:00:00".toDay(),
//            "2022-07-16 09:00:00".toDay()
        )

        val calendar = Calendar.getInstance().apply {
            time = parseFormat.parse(minTime) ?: Date()
        }

        val hourList = mutableListOf<String>().also { hourList ->
            repeat(10) {
                hourList.add(hourFormat.format(calendar.time))
                calendar.add(Calendar.HOUR, 1)
            }
        }.toList()

        val result =
            ResponsePlan(
                availableDate = avaliableDate,
                users = users,
                colors = colors,
                totalCount = 10,
                minTime = minTime,
                maxTime = maxTime,
                timeTableDate = listOf(
                    TimeTableDate(
                        date = "2022-07-09T09:00:00".toDay(),
                        timeTableUnits = listOf(
                            TimeTableUnit(0, 1, listOf(user1.copy()), 1298559478),
                            TimeTableUnit(2, 1, listOf(user2.copy()), 1298559478)
                        )
                    ),

                    TimeTableDate(
                        date = "2022-07-10T09:00:00".toDay(),
                        timeTableUnits = listOf(
                            TimeTableUnit(3, 1, listOf(user3.copy(), user4.copy()), 1298559478)
                        )
                    )
                ),
                hourList = hourList
            )

        return NetworkResult.Success(result)
    }
}
