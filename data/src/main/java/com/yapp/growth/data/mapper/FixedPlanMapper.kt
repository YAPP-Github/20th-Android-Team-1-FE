import com.yapp.growth.data.response.FixedPlanResponse
import com.yapp.growth.domain.entity.Plan.FixedPlan

fun FixedPlanResponse.toFixedPlan(): FixedPlan {
    val response = this
    return FixedPlan(
        id = response.id,
        title = response.promiseName,
        isLeader = response.isOwner,
        category = response.category.keyword,
        members = response.members.map { it.userName },
        place = response.placeName,
        date = response.promiseDate,
    )
}
