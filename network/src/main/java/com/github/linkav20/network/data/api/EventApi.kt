package com.github.linkav20.network.data.api

import retrofit2.http.*
import retrofit2.Response

import com.github.linkav20.network.data.models.CommonAddListToEventIn
import com.github.linkav20.network.data.models.CommonAddListToEventOut
import com.github.linkav20.network.data.models.CommonAddTasksToListIn
import com.github.linkav20.network.data.models.CommonAddUserToPartyIn
import com.github.linkav20.network.data.models.CommonCalculateExpensesIn
import com.github.linkav20.network.data.models.CommonCalculateExpensesOut
import com.github.linkav20.network.data.models.CommonCreateEventIn
import com.github.linkav20.network.data.models.CommonDeleteListIn
import com.github.linkav20.network.data.models.CommonDeleteUserFromPartyIn
import com.github.linkav20.network.data.models.CommonGetEventIn
import com.github.linkav20.network.data.models.CommonGetEventOut
import com.github.linkav20.network.data.models.CommonGetListInfoIn
import com.github.linkav20.network.data.models.CommonGetListInfoOut
import com.github.linkav20.network.data.models.CommonGetPartiesGuestsIn
import com.github.linkav20.network.data.models.CommonGetPartiesGuestsOut
import com.github.linkav20.network.data.models.CommonGetPartyExpensesDeadlineIn
import com.github.linkav20.network.data.models.CommonGetPartyExpensesDeadlineOut
import com.github.linkav20.network.data.models.CommonGetPartyListsIn
import com.github.linkav20.network.data.models.CommonGetPartyListsOut
import com.github.linkav20.network.data.models.CommonGetUserEventsIn
import com.github.linkav20.network.data.models.CommonGetUserPartiesOut
import com.github.linkav20.network.data.models.CommonUpdateEventAddressAdditionalInfoIn
import com.github.linkav20.network.data.models.CommonUpdateEventAddressIn
import com.github.linkav20.network.data.models.CommonUpdateEventAddressLinkIn
import com.github.linkav20.network.data.models.CommonUpdateEventDressCodeIn
import com.github.linkav20.network.data.models.CommonUpdateEventEndTimeIn
import com.github.linkav20.network.data.models.CommonUpdateEventImportantIn
import com.github.linkav20.network.data.models.CommonUpdateEventIsExpensesIn
import com.github.linkav20.network.data.models.CommonUpdateEventMoodboardLinkIn
import com.github.linkav20.network.data.models.CommonUpdateEventStartTimeIn
import com.github.linkav20.network.data.models.CommonUpdateEventThemeIn
import com.github.linkav20.network.data.models.CommonUpdateListTypeIn
import com.github.linkav20.network.data.models.CommonUpdateListVisibilityIn
import com.github.linkav20.network.data.models.CommonUpdatePartyExpensesDeadlineIn
import com.github.linkav20.network.data.models.CommonUpdateTaskNameIn
import com.github.linkav20.network.data.models.CommonUpdateTaskStatusIn
import com.github.linkav20.network.data.models.CommonUpdateUserRoleOnPartyIn
import com.github.linkav20.network.data.models.CommonUploadReceiptInfoNameIn
import com.github.linkav20.network.data.models.CommonUploadReceiptInfoSumIn
import com.github.linkav20.network.data.models.CommonUserExpenses
import com.github.linkav20.network.data.models.CommonGetAllUserReceiptIn
import com.github.linkav20.network.data.models.CommonGetInviteLinkIn
import com.github.linkav20.network.data.models.CommonGetInviteLinkOut
import com.github.linkav20.network.data.models.CommonGetUserReceiptIn
import com.github.linkav20.network.data.models.CommonLoadFinanceStateIn
import com.github.linkav20.network.data.models.CommonLoadFinanceStateOut
import com.github.linkav20.network.data.models.CommonUpdateEventNameIn
import com.github.linkav20.network.data.models.CommonUpdateFinanceStateIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletBankIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletCardIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletOwnerIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletPhoneIn

interface EventApi {
    /**
     * AddUserToParty
     * Добавление пользователя на мероприятие
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/add_user_to_party")
    suspend fun addUserToEvent(@Body input: CommonAddUserToPartyIn): Response<Unit>

    /**
     * CreateEvent
     * Создание мероприятия пользователя
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/create_event")
    suspend fun createEvent(@Body input: CommonCreateEventIn): Response<Unit>

    /**
     * AddListToEvent
     * Добавление нового списка
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonAddListToEventOut]
     */
    @POST("api/add_list_to_event")
    suspend fun createList(@Body input: CommonAddListToEventIn): Response<CommonAddListToEventOut>

    /**
     * AddTaskToList
     * Создание тасок в списке
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/add_task_to_list")
    suspend fun createTasks(@Body input: CommonAddTasksToListIn): Response<Unit>

    /**
     * DeleteList
     * Удаление списка
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/delete_list")
    suspend fun deleteList(@Body input: CommonDeleteListIn): Response<Unit>

    /**
     * DeleteUserFromParty
     * Удаление пользователя с мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/delete_user_from_party")
    suspend fun deleteUserFromEvent(@Body input: CommonDeleteUserFromPartyIn): Response<Unit>

    /**
     * GetAllUserReceipt
     * Получение всех чеков пользователя в одном пдф
     * Responses:
     *  - 200: Success
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/get_all_user_receipts")
    suspend fun getAllUserReceipt(@Body input: CommonGetAllUserReceiptIn): Response<Unit>

    /**
     * GetEvent
     * Получение информации о мероприятии
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetEventOut]
     */
    @POST("api/get_event")
    suspend fun getEvent(@Body input: CommonGetEventIn): Response<CommonGetEventOut>

    /**
     * GetInviteLinkToEvent
     * Получение диплинка на мероприятия (пригласительная ссылка)
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetInviteLinkOut]
     */
    @POST("api/get_invite_link")
    suspend fun getEventInviteLink(@Body input: CommonGetInviteLinkIn): Response<CommonGetInviteLinkOut>

    /**
     * GetListInfo
     * Получение инфы по списку
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetListInfoOut]
     */
    @POST("api/get_list_info")
    suspend fun getListInfo(@Body input: CommonGetListInfoIn): Response<CommonGetListInfoOut>

    /**
     * GetPartiesGuests
     * Получение списка гостей мероприятия
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetPartiesGuestsOut]
     */
    @POST("api/get_parties_guests")
    suspend fun getPartiesGuests(@Body input: CommonGetPartiesGuestsIn): Response<List<CommonGetPartiesGuestsOut>>

    /**
     * GetPartyLists
     * Получение списков мероприятия
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetPartyListsOut]
     */
    @POST("api/get_party_lists")
    suspend fun getPartyLists(@Body input: CommonGetPartyListsIn): Response<List<CommonGetPartyListsOut>>

    /**
     * GetUserEvents
     * Получение мероприятий пользователя
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [kotlin.collections.List<CommonGetUserPartiesOut>]
     */
    @POST("api/get_user_parties")
    suspend fun getUserEvents(@Body input: CommonGetUserEventsIn): Response<kotlin.collections.List<CommonGetUserPartiesOut>>

    /**
     * GetUserReceipt
     * Получение чека пользователя по id чека
     * Responses:
     *  - 200: Success
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/get_user_receipt")
    suspend fun getUserReceipt(@Body input: CommonGetUserReceiptIn): Response<Unit>

    /**
     * UpdateListVisibility
     * Виден ли список гостям
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/is_list_visible_to_guest")
    suspend fun listVisibility(@Body input: CommonUpdateListVisibilityIn): Response<Unit>

    /**
     * LoadFinanceState
     * Получение стадии трат(?) мероприятия (параметр is_expenses)
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonLoadFinanceStateOut]
     */
    @POST("api/load_finance_state")
    suspend fun loadFinanceState(@Body input: CommonLoadFinanceStateIn): Response<CommonLoadFinanceStateOut>

    /**
     * UpdateEventAddress
     * Обновление адреса мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_address")
    suspend fun updateAddress(@Body input: CommonUpdateEventAddressIn): Response<Unit>

    /**
     * UpdateEventAddressAdditionalInfo
     * Обновление доп информации адреса
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_address_additional")
    suspend fun updateAddressAdditionalInfo(@Body input: CommonUpdateEventAddressAdditionalInfoIn): Response<Unit>

    /**
     * UpdateEventAddressLink
     * Обновление ссылки на адрес мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_address_link")
    suspend fun updateAddressLink(@Body input: CommonUpdateEventAddressLinkIn): Response<Unit>

    /**
     * UpdateEventDressCode
     * Обновление дресс-кода
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_dress_code")
    suspend fun updateDressCode(@Body input: CommonUpdateEventDressCodeIn): Response<Unit>

    /**
     * UpdateEventEndTime
     * Обновление окончания времени мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_end_time")
    suspend fun updateEndTime(@Body input: CommonUpdateEventEndTimeIn): Response<Unit>

    /**
     * UpdateEventName
     * Обновление названия мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_name")
    suspend fun updateEventName(@Body input: CommonUpdateEventNameIn): Response<Unit>

    /**
     * UpdateFinanceState
     * Получение стадии трат(?) мероприятия (параметр is_expenses)
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_finance_state")
    suspend fun updateFinanceState(@Body input: CommonUpdateFinanceStateIn): Response<Unit>

    /**
     * UpdateEventImportant
     * Обновление включены ли ты траты на меро
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_important")
    suspend fun updateImportant(@Body input: CommonUpdateEventImportantIn): Response<Unit>

    /**
     * UpdateEventIsExpenses
     * Обновление включены ли ты траты на меро
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_is_expenses")
    suspend fun updateIsExpenses(@Body input: CommonUpdateEventIsExpensesIn): Response<Unit>

    /**
     * UpdateListType
     * Обновление типа списка (EMPTY TODO BUY WISHLIST)
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_list_type")
    suspend fun updateListType(@Body input: CommonUpdateListTypeIn): Response<Unit>

    /**
     * UpdateEventMoodboardLink
     * Обновление ссылки на мудборд
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_moodboard_link")
    suspend fun updateMoodboard(@Body input: CommonUpdateEventMoodboardLinkIn): Response<Unit>

    /**
     * UpdatePartyWalletBank
     * Обновление банка кошелька мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_party_wallet_bank")
    suspend fun updatePartyWalletBank(@Body input: CommonUpdatePartyWalletBankIn): Response<Unit>

    /**
     * UpdatePartyWalletCard
     * Обновление карты кошелька мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_party_wallet_card")
    suspend fun updatePartyWalletCard(@Body input: CommonUpdatePartyWalletCardIn): Response<Unit>

    /**
     * UpdatePartyWalletOwner
     * Обновление владельца кошелька мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_party_wallet_owner")
    suspend fun updatePartyWalletOwner(@Body input: CommonUpdatePartyWalletOwnerIn): Response<Unit>

    /**
     * UpdatePartyWalletPhone
     * Обновление телефона кошелька мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_party_wallet_phone")
    suspend fun updatePartyWalletPhone(@Body input: CommonUpdatePartyWalletPhoneIn): Response<Unit>

    /**
     * UpdateEventStartTime
     * Обновление начала времени мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_start_time")
    suspend fun updateStartTime(@Body input: CommonUpdateEventStartTimeIn): Response<Unit>

    /**
     * UpdateTaskName
     * Обновление названия таски
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_task_name")
    suspend fun updateTaskName(@Body input: CommonUpdateTaskNameIn): Response<Unit>

    /**
     * UpdateTaskStatus
     * Обновление статуса таски (done/not done)
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_task_status")
    suspend fun updateTaskStatus(@Body input: CommonUpdateTaskStatusIn): Response<Unit>

    /**
     * UpdateEventTheme
     * Обновление тематики
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_event_theme")
    suspend fun updateTheme(@Body input: CommonUpdateEventThemeIn): Response<Unit>

    /**
     * UpdateUserRoleOnParty
     * Удаление пользователя с мероприятия
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_user_role_on_party")
    suspend fun updateUserRole(@Body input: CommonUpdateUserRoleOnPartyIn): Response<Unit>

    /**
     * CalculateExpenses
     * Расчет сколько с человека надо собрать, сколько должен конкретный пользователь
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonCalculateExpensesOut]
     */
    @POST("api/calculate_expenses")
    suspend fun calculateExpenses(@Body input: CommonCalculateExpensesIn): Response<CommonCalculateExpensesOut>

    /**
     * GetAllUserExpenses
     * Получение информации по расходам пользователя (без чека, просто из базы) (на выходе массив моделек)
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonUserExpenses]
     */
    @POST("api/get_all_user_expenses")
    suspend fun getAllUserExpenses(@Body input: CommonGetAllUserReceiptIn): Response<CommonUserExpenses>

    /**
     * GetPartyExpensesDeadline
     * Получение текущего дедлайна шага трат
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetPartyExpensesDeadlineOut]
     */
    @POST("api/get_party_expenses_deadline")
    suspend fun getExpensesDeadline(@Body input: CommonGetPartyExpensesDeadlineIn): Response<CommonGetPartyExpensesDeadlineOut>

    /**
     * UpdatePartyExpensesDeadline
     * Обновление дедлайна шага трат
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_party_expenses_deadline")
    suspend fun updatePartyExepensesDeadline(@Body input: CommonUpdatePartyExpensesDeadlineIn): Response<Unit>

    /**
     * UploadReceiptInfoName
     * Обновление названия чека
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_receipt_info_name")
    suspend fun updateReceiptName(@Body input: CommonUploadReceiptInfoNameIn): Response<Unit>

    /**
     * UploadReceiptInfoSum
     * Обновление суммы чека
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_receipt_info_sum")
    suspend fun updateReceiptSum(@Body input: CommonUploadReceiptInfoSumIn): Response<Unit>

}
