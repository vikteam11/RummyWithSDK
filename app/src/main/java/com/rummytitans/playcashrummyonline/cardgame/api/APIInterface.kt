package com.rummytitans.playcashrummyonline.cardgame.api

import com.rummytitans.playcashrummyonline.cardgame.models.*
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants
import com.google.gson.JsonObject
import com.rummytitans.playcashrummyonline.cardgame.models.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface APIInterface {

    @GET("match/v1/get-joined-matches")
    fun getMyContestMatches(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(APP_CODE) appCode: Int,
        @Header(MATCH_TYPE) matchType: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<MatchListModel>

    @GET("match/v1/get-upcoming-matches")
    fun getHomeMatches(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(APP_CODE) appCode: Int,
        @Header(MATCH_TYPE) matchType: Int,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<MatchListModel>



    @GET("match/v1/get-completed-matches")
    fun getCompletedMatches(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(APP_CODE) appCode: Int,
        @Header(MATCH_TYPE) matchType: Int,
        @Header("pageNo") pageNo: Int,
        @Header("records") records: Int
    ): Single<BaseModel<ArrayList<MatchModel>>>

    @GET("notification/notification-key")
    fun getNotificationKey(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
    ): Single<BaseModel<Any>>

    @GET("v1/myteam11/banners")
    fun getHeaders(): Single<HeaderBaseResponse>

    @GET("games/v1/splash")
    fun getVersion(
        @Header(USERID) userId: Int,
        @Header("Version") code: Int,
        @Header("appversion") appversion: Int,
        @Header("AdvertisingId") AdvertisingId: String
    ): Single<BaseModel<VersionModel>>

    @POST("users/get")
    fun loginUser(
        @Header(EMAIL_ADDRESS) email: String,
        @Header(AUTH_EXPIRE) password: String,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<Any>>

    @POST("users/loginwithotp")
    fun requestOtp(
        @Header(MOBILE_NUMER1) mobile: String,
        @Header(LOGIN_AUTH_EXPIRE) auth: String,
        @Header(REFER_CODE) referCode: String,
        @Header(CAMPAIGN_ID1) campaignId: String
    ): Single<BaseModel<Any>>

    @POST("v1/user/change-mobile")
    fun requestOtpForUpdate(
        @Header("UserId") mobile: Int,
        @Header("MobileNumber") auth: String,
        @Header("DeviceID") referCode: String,
        @Header("ExpireToken") campaignId: String,
        @Header("AuthExpire") authExpire: String
    ): Single<BaseModel<Any>>

    @PUT("v1/games/login")
    fun verifyLoginOtp(
        @Header("Mobile") mobile: String,
        @Header("LoginAuthToken") auth: String,
        @Header("ReferCode") referCode: String,
        @Header("CampaignId") campaignId: String,
        @Header("Otp") Otp: String,
        @Header("DeviceID") deviceID: String,
        @Header(TOKEN_FIREBASE_1) FirebaseToken: String,
        @Header("AdvertisingId") AdvertisingId: String,
        @Header("AppsFlyerId") AppsFlyerId: String,
        @Header("Type") type: Int=1
    ): Single<BaseModel<Any>>

    @POST("account/v2/login/truecaller")
    fun loginTrueCaller(
        @Header("Code") Code: String,
        @Header("Verifier") Verifier: String,
        @Header("ReferCode") referCode: String,
        @Header("CampaignId") campaignId: String,
        @Header("DeviceID") deviceID: String,
        @Header(TOKEN_FIREBASE_1) FirebaseToken: String,
        @Header("AdvertisingId") AdvertisingId: String,
        @Header("AppsFlyerId") AppsFlyerId: String
    ): Single<BaseModel<Any>>





    @POST("v1/user/login-email-mobile")
    fun loginWithEmail(
        @Header(EMAIL) email: String,
        @Header(PASSWORD_1) password: String,
        @Header(CAMPAIGN_ID1) campaignId: String,
        @Header(DEVICE_ID_2) deviceID: String,
        @Header("AdvertisingId") AdvertisingId: String,
        @Header("AppsFlyerId") AppsFlyerId: String
    ): Single<BaseModel<Any>>

    @GET("users/usercheck")
    fun checkUser(@Header(EMAIL) email: String, @Header(SID) sId: String): Single<BaseModel<Any>>

    @POST("users/gmaillogin")
    fun loginUserG(
        @Header(EMAIL_ADDRESS) email: String,
        @Header(USER_REFER_CODE) userreferCode: String,
        @Header(TOKEN) token: String,
        @Header(TOKEN_FIREBASE) tokenFireBase: String,
        @Header(GMAIL_ID) GmailID: String,
        @Header(GMAIL_ACCESS_TOKEN) gmailAccessToken: String,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<Any>>

    @GET("v2/games/get/{gameId}")
    fun getGames(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Path("gameId") gameId: Int
    ): Single<BaseModel<GamesResponseModel.GamesModel>>

    @GET("platfrom/gamedetails")
    fun getGameDetails(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(STATE_NAME2) stateName: String,
        @Header(GAME_ID) gameId: Int,
        @Header(LOCATION_CORDINATE) cordinates: String
    ): Single<BaseModel<GamesResponseModel.GamesModel>>

    @POST("users/register")
    fun registerUser(
        @Header(EMAIL_ADDRESS) email: String,
        @Header(PASSWORD) password: String,
        @Header(USER_REFER_CODE) userreferCode: String,
        @Header(TOKEN) token: String,
        @Header(TOKEN_FIREBASE) tokenFireBase: String,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<Any>>

    @POST("users/sentotpnew")
    fun sendOTP(
        @Header(MOBILE_NUMBER) Mobile_Number: String,
        @Header(USERID) UserId: Int,
        @Header(EXPIRETOKEN) ExpireToken: String,
        @Header(AUTHEXPIRE) AuthExpire: String
    ): Single<BaseModel<Any>>


    @GET("v3/match/getuserofferdetails")
    fun getOfferList(
        @Header(USERID) UserId: Int,
        @Header(EXPIRETOKEN) ExpireToken: String,
        @Header(AUTHEXPIRE) AuthExpire: String
    ): Single<OfferListModel>

    @POST("users/forgotpasswordnew")
    fun forgotPasswordSendOTP(
        @Header(MOBILE_NUMBER) Mobile_Number: String,
        @Header(TOKEN) token: String
    ): Single<BaseModel<Any>>

    @PUT("users/verifyOtpnew")
    fun verifyOTP(
        @Header(MOBILE_NUMBER) mobileNumber: String,
        @Header(OTP) otp: String,
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String
    ): Single<BaseModel<Any>>

    @PUT("users/verifyOtpnew")
    fun verifyOtpForUpdateMobile(
        @Header(MOBILE_NUMBER) mobileNumber: String,
        @Header(OTP) otp: String,
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String
    ): Single<BaseModel<Any>>

    @POST("users/resetotpverifynew")
    fun forgotPasswordVerifyOTP(
        @Header(MOBILE_NUMBER) mobileNumber: String,
        @Header(OTP) otp: String,
        @Header(TOKEN) token: String
    ): Single<BaseModel<Any>>

    @POST("users/resetpasswordnew")
    fun resetPassword(
        @Header(MOBILE_NUMBER) mobileNumber: String,
        @Header(OTP1) otp: String,
        @Header(TOKEN_1) token: String,
        @Header(PASSWORD_1) password: String
    ): Single<BaseModel<Any>>

    @POST("users/updateuserpincode")
    fun updatePincode(
        @Header(AUTHEXPIRE) mobileNumber: String,
        @Header(EXPIRETOKEN) otp: String,
        @Header(USERID) token: String,
        @Header(PINCODE) password: String,
        @Header(DOB) dob: String
    ): Single<BaseModel<Any>>

    @GET("users/teamcheck")
    fun checkTeamnameAvailability(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(TEAMNAME) teamName: String
    ): Single<BaseModel<Any>>

    @GET("v3/team/getteamsuggestions")
    fun checkTeamnameSuggestions(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String
    ): Observable<BaseModel<String>>

    @GET("account/v1/states")
    fun getStateList(): Single<BaseModel<ArrayList<StateModel>>>

    @PUT("users/updateteamname")
    fun updateCompleteProfile(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(NAME) name: String,
        @Header(TEAMNAME1) teamName: String,
        @Header(STATE_NAME) stateName: String,
        @Header(AVATAR_ID) avatarId: Int
    ): Single<BaseModel<String>>

    @GET("users/getuserprofile")
    fun getProfile(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String
    ): Single<BaseModel<Any>>

    @GET("leauge/getmatchleague")
    fun getCategories(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<CategoryResponse>

    @GET("leauge/getmatchCatleague")
    fun getCategoriesContest(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CATEGORY_ID) categoryId: Int,
        @Header(MATCH_ID) matchId: Int,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<CategoryContestsModel>

    @GET("v2/leauge/detail")
    fun getContestInfo(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) categoryId: Int,
        @Header(MATCH_ID) matchId: Int
    ): Single<ContestInfoModel>

    @GET("player/getteamlistv2")
    fun getTeamList(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<ArrayList<TeamModel>>>

    @GET("player/getjointeamlistv2")
    fun getJoinTeamList(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) categoryId: Int,
        @Header(MATCH_ID) matchId: Int,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<ArrayList<TeamModel>>>


    @GET("myprofile/get-subscription-items")
    fun getSubscriptionList(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header("Initial") initial: Boolean
    ): Single<BaseModel<ArrayList<SubscriptionItemModel>>>

    @POST("myprofile/save-subscription-items")
    fun saveSubscriptionList(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(TOPIC_NAMES) TopicNames: String
    ): Single<SaveSubscriptionModel>

    @GET("player/getplayerlist")
    fun getCreateTeamPlayerList(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<PlayerList>

    @GET("player/geteditplayerlistv1")
    fun getEditTeamPlayerList(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(TEAM_ID) teamId: Long,
        @Header(LANGUAGE_CODE_1) languageCode: String,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<PlayerList>

    @POST("player/userteamsave")
    fun saveTeam(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(PLAYER_RESP) playerRes: String,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<String>>

    @POST("player/userteamsavev1")
    fun saveTeamNew(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(PLAYER_RESP) playerRes: String,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<String>>

    @POST("v1/user/update-team-name")
    fun updateTeamName(
        @Header(USERID) userId: Int,
        @Header(TEAMNAME) teamName: String,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(DEVICE_ID_2) deviceID: String
    ): Single<BaseModel<String>>

    @POST("player/usereditsave")
    fun saveEditTeam(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(PLAYER_RESP) playerRes: String,
        @Header(TEAM_ID) teamId: Long,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<String>>
    @POST("player/usereditsavev1")
    fun saveEditTeamNew(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(PLAYER_RESP) playerRes: String,
        @Header(TEAM_ID) teamId: Long,
        @Header(CAMPAIGN_ID) campaignId: String
    ): Single<BaseModel<String>>

    @POST("joinleauge/usableandamountNewv2")
    fun getUsableJoinAmount(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) contestId: Int,
        @Header(CONTEST_MEMBERS) contestMembers: Int,
        @Header(CONTEST_FEES) contestFee: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String,
        @Header("teamCount") teamCount: Int,
        @Header("passcount") passcount: Int,
        @Header("Modipasscount") Modipasscount: Int = 0
    ): Single<BaseModel<UsableAmountModel>>


    @POST("joinleauge/confirmv1")
    fun getNewUsableJoinAmount(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) contestId: Int,
        @Header(CONTEST_MEMBERS) contestMembers: Int,
        @Header(CONTEST_FEES) contestFee: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String,
        @Header("teamCount") teamCount: Int,
        @Header("passcount") passcount: Int,
        @Header("Modipasscount") Modipasscount: Int = 0
    ): Single<BaseModel<JoinGameConfirmationModel>>

    @POST("goldenticket/confirmgoldenticket")
    fun getGoldenPassConfirmDetails(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header("PassId") passID: String
    ): Single<BaseModel<JoinGameConfirmationModel>>

    @POST("quiz/usablequizamount")
    fun getUsableJoinAmountForQuiz(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) contestId: Int,
        @Header(CONTEST_MEMBERS) contestMembers: Int,
        @Header(CONTEST_FEES) contestFee: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String
    ): Single<BaseModel<UsableAmountModel>>

    @POST("joinleauge/joinleaguev2")
    fun joinContest(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(TEAM_ID) teamId: String,
        @Header(CONTEST_ID) contestId: Int,
        @Header(MATCH_ID) matchId: Int,
        @Header(CAMPAIGN_ID) campaignId: String,
        @Header("PassCount") passCount: Int = 0
    ): Single<BaseModel<String>>


    @POST("goldenticket/purchase")
    fun purchaseCoupon(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header("PassId") passID: String
    ):Single<BaseModel<String>>

    @POST("quiz/joinquiz")
    fun joinQuiz(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) contestId: Int,
        @Header(MATCH_ID) matchId: Int,
        @Header(CAMPAIGN_ID) campaignId: String,
        @Header(LANGUAGE_CODE_1) languageCode: String,
        @Header(TOKEN_FIREBASE_1) FirebaseToken: String = ""
    ): Single<BaseModel<String>>

    @POST("quiz/real-time-join-quiz")
    fun joinRealTimeQuiz(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) contestId: Long,
        @Header(MATCH_ID) matchId: Int,
        @Header(CAMPAIGN_ID) campaignId: String,
        @Header(LANGUAGE_CODE_1) languageCode: String,
        @Header(TOKEN_FIREBASE_1) FirebaseToken: String = ""
    ): Single<RealTimeJoinModel>

    @GET("leaugedetail/WinningMessage")
    fun getWinningMessage(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) contestId: Int,
        @Header(MATCH_Id) matchId: Int
    ): Single<BaseModel<WinningMessageResponseModel>>

    @GET("leaugedetail/pdfcheck")
    fun fetchPdf(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Query(CONTEST_ID) contestId: Int,
        @Query(MATCH_Id) matchId: Int
    ): Single<BaseModel<PDFResponseModel>>

    @GET("leauge/getleaugeandrankv1")
    fun getWinningBreakDown(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) contestId: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String
    ): Single<WinningBreakupModel>

    @GET("leauge/getleaugeandrankv2")
    fun getWinningBreakDownV2(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_ID) contestId: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String
    ): Single<WinningBreakupModel>

    @GET("leauge/getmyjoin")
    fun getJoinedContests(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int
    ): Single<BaseModel<ArrayList<LeagueData>>>

    @POST("joinleauge/switchteam")
    fun switchTeam(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(CONTEST_ID) contestId: Int,
        @Header(TEAM_ID) teamId: Long,
        @Header(OLD_TEAM_ID) oldTeamId: Long
    ): Single<BaseModel<String?>>

    @GET("private/privatedetail")
    fun getPrivateContestConfig(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String
    ): Single<BaseModel<PrivateContestConfigResponse>>

    @POST("private/createprivatev2")
    fun createPrivateContest(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(CONTEST_FEES) contestFee: Double,
        @Header(CONTEST_WINNING_AMOUNT) contestWinningAmount: Int,
        @Header(CONTEST_MEMBERS) contestMembers: Int,
        @Header(TEAM_ID) teamId: String,
        @Header(CONTEST_IS_MULTIPLE) isMultiple: Int,
        @Header("ConfirmContest") isConfirm: Int,
        @Header(CAMPAIGN_ID) campaignId: String,
        @Header("RankData") RankData: String
    ): Single<BaseModel<CreatePrivateContestModel>>

    @POST("private/createprivatecal")
    fun getPrivateContestWinningBreakup(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_WINNING_AMOUNT) winningAmount: String,
        @Header(CONTEST_MEMBERS) member: String
    ): Single<BaseModel<ArrayList<ManualWinningResponse>>>

    @POST("private/leaugecode")
    fun getPrivateContestDetails(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(CONTEST_CODE) leaugeCode: String
    ): Single<BaseModel<CreatePrivateContestModel>>

    @GET("match/v1/getmatch")
    fun getSingleMatchDetails(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(MATCH_ID) matchId: Int,
        @Header(APP_CODE) appCode: Int=2,
        @Header(MATCH_STATUS) isMatchStatusLive: String=MyConstants.MATCH_NOT_STARTED
    ): Single<BaseModel<MatchModel>>

    @POST("/teampreview/preview")
    fun getTeamPreviewPlayers(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(TEAM_ID) teamId: Long,
        @Header(MATCH_ID) matchId: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String
    ): Single<BaseModel<TeamPreviewModel>>

    @POST("/teampreview/previewV1")
    fun getNewTeamPreviewPlayersList(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(TEAM_ID) teamId: Long,
        @Header(MATCH_ID) matchId: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String
    ): Single<BaseModel<NewTeamPlayersModel>>

    @POST("/teampreview/previewV3")
    fun getNewTeamPreviewPlayersListV2(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(TEAM_ID) teamId: Long,
        @Header(MATCH_ID) matchId: Int,
        @Header(LANGUAGE_CODE_1) languageCode: String
    ): Single<BaseModel<NewTeamPreviewResponse>>

    @GET("/player/getplayerinfo")
    fun getPlayerDetails(
        @Header(USERID) userId: Int,
        @Header(EXPIRETOKEN) expireToken: String,
        @Header(AUTHEXPIRE) authExpire: String,
        @Header(PLAYER_ID) teamId: Int
    ): Single<BaseModel<PlayerInfoModel>>

    @GET("transaction/v2/account-balance")
    fun getWalletIno(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(LANGUAGE_CODE_1) languageCode: String
    ): Single<BaseModel<WalletInfoModel>>

    @POST("payment/v1/calculate/gst")
    fun getGstCalcualtion(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRETOKEN) AuthExpire: String,
        @Header(AUTHEXPIRE) ExpireToken: String,
        @Body json:JsonObject
    ): Single<BaseModel<GstCalculationModel>>

    @POST("myprofile/redeem-coupan")
    fun redeemCode(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(COUPON) coupon: String,
        @Header(DEVICE_ID_2) deviceId: String
    ): Single<BaseModel<String>>

    @GET("users/getuserprofile")
    fun getProfileIno(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<ProfileInfoModel>>

    @GET("myprofile/getlevels")
    fun getLevels(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<LevelModel>>

    @POST("myprofile/userprofile")
    fun getVerificationInfo(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<ProfileVerificationModel>>

    @POST("bankaccount/bankdelete")
    fun deleteBank(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<Any>>

    @POST("myprofile/verificationmailrummy")
    fun sendVerificationEmail(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<String>>

    @POST("myprofile/emailchange")
    fun updateEmailAddress(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("NewEmail") newEmail: String
    ): Single<BaseModel<String>>

    @Multipart
    @POST("pancard/pancardupload")
    fun addPanCardDetail(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Part(PAN_NAME) name: RequestBody,
        @Part(PAN_NUMBER) number: RequestBody,
        @Part(PAN_DOB) dob: RequestBody,
        @Part(PAN_STATE) state: RequestBody,
        @Part(PAN_PHOTO + "\"; filename=abc.jpg") file: RequestBody
    ): Single<BaseModel<String>>

    @Multipart
    @POST("profile/v2/upload/address-proof")
    fun uploadAddressProofFromAadhaar(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Part("DocType") DocType:RequestBody,
        @Part(PAN_STATE) state: RequestBody,
        @Part images: ArrayList<MultipartBody.Part>
    ): Single<BaseModel<AddressResponseModel<Boolean>>>

    @POST("kyc/v1/dl/verification")
    fun uploadAddressProofFromDL(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Body jsonBody:JsonObject
    ): Single<BaseModel<AddressResponseModel<Int>>>

    @GET("kyc/v1/aadhar/init")
    fun initAadhaar(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
    ): Single<BaseModel<String>>

    @GET("kyc/v1/content")
    fun getKycContent(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(LANGUAGE_CODE_1)language:String
    ): Single<BaseModel<AddressKycContentModel>>

    @POST("/kyc/v1/aadhar/basic-verification")
    fun verifyWithAadhaar(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Body json: JsonObject
    ): Single<BaseModel<AddressResponseModel<Int>>>

    @Multipart
    @POST("bank/uploadbank")
    fun addBankDetail(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Part(BANK_USER_NAME) name: RequestBody,
        @Part(BANK_ACC_NUMBER) bank_acc_number: RequestBody,
        @Part(BANK_IFSC_CODE) ifsc: RequestBody,
        @Part(BANK_NAME) bname: RequestBody,
        @Part(BANK_BRANCH) branch: RequestBody,
        @Part(BANK_PHOTO + "\"; filename=" + "abc.jpg") file: RequestBody
    ): Single<BaseModel<String>>

    @POST("myprofile/gettransaction")
    fun getTranscationHistory(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(TRANS_TYPE_ID) typeId: String
    ): Single<BaseModel<ArrayList<TransactionModel.TransactionListModel>>>

    @GET("transaction/v2/list/{TypeId}/{PageNo}")
    fun getRecentTransactions(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Path("PageNo") PageNo: String,
        @Path("TypeId") TypeId: Int
    ): Single<BaseModel<ArrayList<TransactionModel>>>

    @GET("transaction/v3/detail/{tranId}/{gameId}")
    fun getTransectionDetails(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("TranType") typeId: String,
        @Path("tranId") txtId: String,
        @Path("gameId") gameId: String,
    ): Single<BaseModel<TransactionModel.TransactionListModel>>

    @GET("pass/get-passes")
    fun getPasses(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<CouponResponseModel>>

    @GET("pass/get-user-history")
    fun getPassHistory(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("passID") passID: Int
    ): Single<BaseModel<ArrayList<CouponHistoryModel>>>

    @GET("v2/games/refer-list")
    fun getReferList(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<ReferModel>

    @FormUrlEncoded
    @POST("/users/save-friends")
    fun saveContacts(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Field(FRIENDS_DATA) list: String,
        @Header("DeviceID") devideId: String
    ): Single<SavedContactModel>

    @GET("/users/get-friends")
    fun getContacts(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("DeviceID") devideId: String
    ): Single<MyContactsResponseModel>

    @POST("quiz/get-wheel-data")
    fun getWheelData(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("SportsType") SportsType: String,
        @Header("matchId") matchId: Int
    ): Single<BaseModel<WheelDataModel>>

    @POST("quiz/get-wheel-data-Completed")
    fun getWinnerWheelData(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("SportsType") SportsType: String,
        @Header("matchId") matchId: Int
    ): Single<BaseModel<WheelDataModel>>

    @POST("quiz/update-wheel-data")
    fun updateWheelData(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("WheelID") SportsType: String
    ): Single<BaseModel<Any>>

    @POST("users/updateuserprofile")
    fun updateProfile(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(UserName) name: String,
        @Header(DOB) dob: String,
        @Header(GENDER) gender: String,
        @Header(ADDRESS) address: String,
        @Header(PINCODE) pincode: String,
        @Header(STATE) state: String,
        @Header(EMAIL) email: String
    ): Single<BaseModel<String>>

    @POST("myprofile/updateavtarimage")
    fun changeUserAvtaar(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(AVTAR_ID) avtarId: String
    ): Single<BaseModel<String>>

    @POST("users/chnagepassword")
    fun changePassword(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(OLD_PASSWORD) oldpassword: String,
        @Header(PASSWORD_2) password: String
    ): Single<BaseModel<String>>

    @POST("notification/getnotificationv2")
    fun getNotificationList(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<NotificationModel>

   // @POST("myprofile/withdrawV1")
    @POST("transaction/v1/withdraw")
    fun withdrawMoney(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Body map:JsonObject,
       //@Header(AMMOUNT) ammount: String,
        //@Header(WITHDRAWAL_METHOD) methodId: String
    ): Single<BaseModel<String>>

    @GET("myprofile/Wddetail")
    fun getWithdrawalDetail(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("TnxId") transactionID: String
    ): Single<BaseModel<WithdrawalDetailModel>>

    @POST("myprofile/WdRquestCancel")
    fun cancelWithdrawalDetail(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("TnxId") transactionID: String
    ): Single<BaseModel<String>>

    @POST("myprofile/withdrawunutilized")
    fun withdrawUnutilizedAmount(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(AMMOUNT) ammount: String,
        @Header(WITHDRAWAL_METHOD) methodId: String
    ): Single<BaseModel<String>>

    @GET("match/getsporttypeV7")
    fun getNewSportTab(
        @Header(USER_ID) UserId: Int,
        @Header(LANGUAGE_CODE) langCode: String,
        @Header(APP_CODE) appCode: Int
    ): Single<BaseModel<ArrayList<SportTabs>>>

    @GET("leaugedetail/livemyjoin")
    fun getCompletedContests(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(MATCH_ID) matchId: Int
    ): Single<BaseModel<CompletedContestsModel>>

    @GET("leaugedetail/getplayerstates")
    fun getLeaguePlayerInfo(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(CONTEST_ID) contestId: Int
    ): Single<BaseModel<ArrayList<LeaguePlayerInfoModel>>>

    @GET("leaugedetail/getplayerstates/{matchId}")
    fun getCompletedContestLeaguePlayerInfo(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Path("matchId") matchID: Int
    ): Single<BaseModel<ArrayList<LeaguePlayerInfoModel>>>

    @GET("{code}")
    fun getBankDetail(@Path("code") code: String): Single<IFSCCodeModel>

   // @POST("myprofile/getbonudamount")
    @GET
    fun getCashBonusList(
       @Url url: String,
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<ArrayList<CashBonusModel>>>

    @GET("/payment/v1/offers")
    fun getAddCashOfferList(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<AddCashOfferModel>>


    //user Version 120
    @POST("payment/v1/gateway/initialize")
    fun getPaymentGateWay(
        @Header(USERID) userId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Body map:JsonObject
    ): Single<NewPaymentGateWayModel>

    @POST("payment/deletecard")
    fun deleteNewCard(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Query(CARD_TOKEN) cardToken: String
    ): Single<DeleteCardModel>

    @FormUrlEncoded
    @POST("upi/verify-vpa")
    fun verifyUPI(
        @Field("merchant_id") merchant_id: String,
        @Field("vpa") vpa: String
    ): Single<VerifyUPIModel>

    @GET("match/gettime")
    fun getTime(): Single<String>

    @GET("payment/v1/card/validator")
    fun checkCardInformation(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) AuthExpire: String,
        @Header(AUTH_EXPIRE) ExpireToken: String,
        @Header(CARD_NUMBER) number: String,
        @Header(CARD_TYPE) type: String="1"
    ): Single<CardValidationModel>

    @GET("PaymentsAndroid/DeleteCard")
    fun deleteCard(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Query(CARD_TOKEN) cardToken: String
    ): Single<Boolean>

    @GET("PaymentsAndroid/CardToken")
    fun tokenizeCard(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Query("cardNumber") cardNumber: String,
        @Query("year") year: String,
        @Query("month") month: String,
        @Query("cvv") cvv: String
    ): Single<CardTokenizeResponse>

    @GET("PaymentsAndroid/AddCustomerCard")
    fun saveCard(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Query("cardNumber") cardNumber: String,
        @Query("year") year: String,
        @Query("month") month: String
    ): Single<CardTokenizeResponse>

    @POST("myprofile/savefb")
    fun submitFeedback(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(TITLE) title: String,
        @Header(MESSAGE) mes: String,
        @Header(CATEGORY_NAME) category: String
    ): Single<BaseModel<String>>

    @POST("users/saveipaddress")
    fun logout(
        @Query(USER_ID) UserId: String,
        @Query(IP_ADDRESS) ipaddress: String,
        @Query(MAC_ADDRESS) macaddress: String,
        @Query(TYPE) type: String
    ): Single<BaseModel<String>>

    @POST("v1/user/logout-all-devices")
    fun logoutFromAllDevice(
        @Header("UserId") UserId: String,
        @Header("ExpireToken") expire: String,
        @Header("AuthExpire") auth: String
    ): Single<BaseModel<String>>

    @POST("v2/leaderboard/profile")
    fun getProfileSeriesRank(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(SPORT_TYPE) type: String
    ): Single<BaseModel<SeriesRankModel>>

    @POST("v2/leaderboard/seriesdetail")
    fun getSeriesDetail(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(SERIES_ID) id: String
    ): Single<BaseModel<RankModel>>

    @POST("v2/leaderboard/seriesmatch")
    fun getSeriesMatchList(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(SERIES_ID) id: String
    ): Single<BaseModel<ArrayList<LeaderBoardPlayerInfoModel>>>

    @POST("leaderboard/get-wheel")
    fun getLeaderBoeardWheelData(
        @Header("UserID") UserId: Int,
        @Header("ExpireToken") ExpireToken: String,
        @Header("AuthExpire") AuthExpire: String,
        @Header("SeriesID") id: Int,
        @Header("SportsType") sportID: String
    ): Single<BaseModel<WheelDataModel>>

    @POST("v2/team/teampreview")
    fun getTeamPreviewRank(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(TEAM_ID) id: Long
    ): Single<BaseModel<NewTeamPreviewResponse>>

    @GET("v3/match/getpoll")
    fun getPoll(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(POLL_ID) pollId: String
    ): Single<BaseModel<PollResponseModel>>

    @GET("v3/match/getpolllist")
    fun getPollList(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<ArrayList<PollListModel>>>

    @GET("v3/match/pollsave")
    fun submitPoll(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(POLL_ID) pollId: String,
        @Header(POLL_OPTION_ID) optionId: String
    ): Single<BaseModel<String>>

    @GET("myprofile/getwithdrawaloptionsV2")
    fun getWithdrawalMethods(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<Array<WithdrawalMethodModel>>>

    //@GET("myprofile/getwdoptionsV3")
    @GET("transaction/v1/withdrawal/options")
    fun getWithdrawalOptions(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Query(LANGUAGE_CODE_1) languageCode: String
    ): Single<BaseModel<WithdrawModel>>

    @GET("v3/match/pollsaveshare")
    fun shareScratchCoupon(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(POLL_ID) pollId: String,
        @Header(POLL_OPTION_ID) optionId: String
    ): Single<BaseModel<String>>

    @POST("v3/language/save")
    fun saveLanguage(
        @Query(USER_ID) UserId: Int,
        @Query(DEVICE_ID) deviceId: String,
        @Query(LANGUAGE_CODE) lamguageCode: String,
        @Query(DEVICE_TYPE) type: String
    ): Single<BaseModel<String>>

    @GET("v4/team/teamlist")
    fun getFavTeamList(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<ArrayList<FavroiteTeamListModel>>>

    @POST("v3/team/saveteam")
    fun saveFavTeam(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(FAV_TEAM_ID) teamId: Long,
        @Header(TOUR_ID) tourId: Int
    ): Single<BaseModel<String>>

    @GET("v3/player/liveplayerlistv4")
    fun getTeamPlayerBreakup(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(TEAM_ID) teamId: Long,
        @Header(INNING) inning: Int,
        @Header(CONTEST_ID) leaugeId: Int,
        @Header(MATCH_Id) matchId: Int
    ): Single<PlayerPointBreakupModel>

    @GET("/v3/player/getsingleplayerv4")
    fun getPlayerBreakup(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(PLAYER_ID) playerId: Int,
        @Header(INNING) inning: Int,
        @Header(CONTEST_ID) leaugeId: Int,
        @Header(MATCH_Id) matchId: Int
    ): Single<PlayerPointBreakupModel>

    @POST("myprofile/AddCashOffer")
    fun getAddCashBannerOffer(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<ArrayList<WalletInfoModel.Offer>>>

    @POST("/user/v2/Withdrawal/tds-calculation")
    fun getTdsOnAmount(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(LANGUAGE_CODE_1) languageCode: String,
        @Body json: JsonObject
    ): Single<BaseModel<List<WithdrawalTdsModel>>>

    @GET("myprofile/getscarchcardlist")
    fun getScratchCardList(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<ArrayList<ScrachCardListModel>>>

    @POST("myprofile/ScarchCard")
    fun redeemScratchCard(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(ID) cardId: Long
    ): Single<BaseModel<String>>

    @GET("users/savefirebasetoken")
    fun updateFirebaseToken(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(TOKEN_FIREBASE_1) FirebaseToken: String
    ): Single<BaseModel<String>>

    @POST("teampreview/comparev2")
    fun compareTeam(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(MATCH_Id) matchId: Int,
        @Header(TEAM_ID) teamId: Long,
        @Header(COMPARE_TEAM_ID) compareteamId: Long,
        @Header(CONTEST_ID) leaugeId: Int
    ): Single<BaseModel<CompareTeamModel>>

    @GET("myprofile/gethelpdesk")
    fun getHelpDesk(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<HelpDeskModel>>

    @GET("myprofile/getscarchcard")
    fun getScratchCard(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<String>>

    @POST("myprofile/updatescarchcard")
    fun updateScratchCard(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<String>>

    @GET("team/get-team-sports-criteria")
    fun getCreateTeamSettings(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(LANG_CODE) langCode: String,
        @Header(SPORTS_ID) sportsId: Int,
        @Header(MATCH_ID) matchId: Int
    ): Single<BaseModel<CreateTeamSettingsModel>>

    @GET("match/prediction")
    fun getMatchPrediction(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(MATCH_ID) matchId: Int
    ): Single<BaseModel<PredictionResponseModel>>

    @GET("Quiz/QuizQuestionList")
    fun getQuizQuestions(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(MATCH_Id) matchId: Int,
        @Header(LANGUAGE_CODE_1) langCode: String
    ): Single<BaseModel<ArrayList<QuizQuestionsModel>>>

    @POST("quiz/answerquiz")
    fun submitAnswer(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(MATCH_Id) matchId: Int,
        @Header(QUESTION_ID) questionId: Int,
        @Header(OPTION_ID) optionId: Int
    ): Single<BaseModel<Any>>

    @GET("Quiz/QuizQuestionAnswerList")
    fun getResult(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(MATCH_Id) matchId: Int,
        @Header(LANGUAGE_CODE_1) langCode: String
    ): Single<BaseModel<QuizResultModel>>

    @GET("Quiz/CheckQuizCompletTime")
    fun getQuizTime(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(MATCH_Id) matchId: Int
    ): Single<BaseModel<String>>

    @GET("player/getjoinedteamlist/v1/{matchId}")
    fun getJoinedTeams(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Path(MATCH_Id) matchId: String
    ): Single<BaseModel<ArrayList<TeamModel>>>

    @GET("api/scorecard/getdetail/{UserId}/{matchId}")
    fun getFullScoreboard(
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Path(USER_ID) UserId: Int,
        @Path(MATCH_Id) matchId: String
    ): Single<BaseModel<ArrayList<MatchInnings>>>

    @POST("coupans/list")
    fun getCouponsList(
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(USER_ID) UserId: Int
    ): Single<BaseModel<ArrayList<AvailableCouponModel>>>

    @POST("coupans/check-availability")
    fun checkCouponCodeAvailability(
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(USER_ID) UserId: Int,
        @Header(ID) couponId: String
    ): Single<BaseModel<Any>>

    @POST("payment/v1/coupan/verify")
    fun verifyAppliedCoupon(
        @Header(USER_ID) userId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Body jsonObject: JsonObject
    ): Single<BaseModel<CouponAppliedModel>>

    @GET("api/chat/get-messages/{UserId}/{MatchId}/{leagueId}/{index}")
    fun getChatMessages(
        @Path(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Path("leagueId") leagueId: String,
        @Path(INDEX) index: String,
        @Path("MatchId") MatchId: String
    ): Single<BaseModel<ChatResponse>>

    @POST("v1/pinpoint/register")
    fun updateValuesOnPinPoints(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header("DemographicData") DemographicData: String,
        @Header("LocationData") LocationData: String,
        @Body raw: JsonObject
    ): Single<BaseModel<Any>>


    @POST("user/v1/get/jwt-token/{UserId}")
    fun getJWTToken(
        @Path(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<JWTTokenResponse>

    @POST(" /apis/v2/")
    fun getPokerData(@Body raw: JsonObject): Single<PokerDataResponse>


    @GET("v1/get/token-myteam/{UserId}")
    fun getPokerToken(
        @Path(USER_ID) UserId: Int,
        @Header(AUTH_EXPIRE) ExpireToken: String,
        @Header(EXPIRE_TOKEN) AuthExpire: String
    ): Single<JWTTokenResponse>

    @POST("games/v1/tickets")
    fun getGamesTicket(
        @Header(USER_ID) UserId: Int,
        @Header(AUTH_EXPIRE) ExpireToken: String,
        @Header(EXPIRE_TOKEN) AuthExpire: String,
        @Header("GameId") GameId: Int
    ): Single<BaseModel<GameTicketModel>>

    @POST("payment/v1/wallet/authenticate")
    fun authenticateWallet(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Body map:JsonObject
    ): Single<BaseModel<Any>>

    @PUT("payment/v1/card/delete")
    fun deleteSaveCard(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Header(CARD_TOKEN) cardToken: String
    ): Single<DeleteCardModel>

    @POST("payment/v1/wallet/direct-debit")
    fun directDebit(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) AuthExpire: String,
        @Header(AUTH_EXPIRE) ExpireToken: String,
        @Body map:JsonObject
    ): Single<BaseModel<String>>

    @POST("payment/v1/vpa/verify")
    fun verifyUPI(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRE_TOKEN) AuthExpire: String,
        @Header(AUTH_EXPIRE) ExpireToken: String,
        @Header("UPI") UPI: String,
    ): Single<BaseModel<String>>

    @GET("profile/v1/wallet/balance")
    fun getWalletBalance(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<WalletBalanceModel>>

    @GET("v2/banner/")
    fun getBanner(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRETOKEN) AuthExpire: String,
        @Header(AUTHEXPIRE) ExpireToken: String,
    ): Single<BaseModel<ArrayList<HeaderItemModel>>>

    @GET("v2/category/")
    fun getCategories(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRETOKEN) AuthExpire: String,
        @Header(AUTHEXPIRE) ExpireToken: String,
    ): Single<BaseModel<ArrayList<RummyCategoryModel>>>

    @GET("v2/lobby/list")
    fun getLobbies(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRETOKEN) AuthExpire: String,
        @Header(AUTHEXPIRE) ExpireToken: String,
        @Query("categoryId")categoryId:String,
    ): Single<BaseModel<ArrayList<RummyLobbyModel>>>

    @POST("v2/wallet/buy-in-range")
    fun confirmLobby(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRETOKEN) AuthExpire: String,
        @Header(AUTHEXPIRE) ExpireToken: String,
        @Body json:JsonObject
    ): Single<BaseModel<JoinGameConfirmationModel>>

    @POST("v2/game/play")
    fun joinLobby(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRETOKEN) AuthExpire: String,
        @Header(AUTHEXPIRE) ExpireToken: String,
        @Body json:JsonObject
    ): Single<BaseModel<JoinLobbyModel>>

    @POST("v2/game/active-match")
    fun checkForActiveMatch(
        @Header(USER_ID) UserId: Int,
        @Header(EXPIRETOKEN) AuthExpire: String,
        @Header(AUTHEXPIRE) ExpireToken: String,
        @Body json:JsonObject
    ): Single<BaseModel<JoinLobbyModel>>

    @GET("games/v1/wallet/details")
    fun getWalletDetails(
        @Header(USER_ID) UserId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String
    ): Single<BaseModel<WalletBalanceModel>>


    /*Location*/
    @POST("account/v1/location")
    fun checkUserIsBanned(
        @Header(USER_ID) userId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Body raw: JsonObject
    ): Single<BaseModel<Any>>

    @GET("games/v1/refer-content")
    fun getReferContent(
        @Header(USER_ID) userId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
    ): Single<BaseModel<ReferContentModel>>


    @GET("v2/rakeback/details")
    fun getRakeBackDetail(
        @Header(USER_ID) userId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
    ): Single<BaseModelGame<RakeBackDetailModel>>

    @POST("v2/rakeback/reedem")
    fun redeemRakeBack(
        @Header(USER_ID) userId: String,
        @Header(EXPIRE_TOKEN) ExpireToken: String,
        @Header(AUTH_EXPIRE) AuthExpire: String,
        @Body json :JsonObject
    ): Single<BaseModelGame<Any>>

    @POST("print-log")
    fun printRummyLog(
        @Body json:JsonObject
    ): Single<BaseModel<JsonObject>>

    companion object {
        const val TRANSACTION_ID = "TxnId"
        const val JOINED_MATCH_ID = "UserJoinedMatchId"
        const val LEAGUE_ID = "LeagueId"
        const val INDEX = "index"
        const val EMAIL_ADDRESS = "email_address"
        const val PASSWORD = "password_hash"
        const val PASSWORD_2 = "passowrd"
        const val PASSWORD_1 = "Password"
        const val EMAIL = "email"
        const val SID = "sId"
        const val USER_REFER_CODE = "userreferCode"
        const val REFER_CODE = "ReferCode"
        const val TOKEN = "token"
        const val TOKEN_1 = "Token"
        const val TOKEN_FIREBASE = "tokenFireBase"
        const val TOKEN_FIREBASE_1 = "FireBaseToken"
        const val GMAIL_ID = "GmailID"
        const val GMAIL_ACCESS_TOKEN = "GmailAccessToken"
        const val FACEBOOK_ID = "FacebokkId"
        const val FACEBOOK_ACCESS_TOKEN = "FacebookAccessToken"
        const val MOBILE_NUMBER = "Mobile_Number"
        const val MOBILE = "Mobile"
        const val MOBILE_NUMER1 = "MobileNumber"
        const val USERID = "UserId"
        const val OTP = "Otp"
        const val OTP1 = "OTP"
        const val EXPIRETOKEN = "ExpireToken"
        const val AUTHEXPIRE = "AuthExpire"
        const val TEAMNAME = "teamName"
        const val TEAMNAME1 = "Team_Name"
        const val STATE_NAME = "State_Name"
        const val NAME = "Name"
        const val AVATAR_ID = "AvtaarID"
        const val APP_CODE = "appCode"
        const val MATCH_STATUS = "Status"
        const val MATCH_TYPE = "matchType"
        const val MATCH_ID = "matchID"
        const val MATCH_Id = "matchId"
        const val CATEGORY_ID = "catId"
        const val CONTEST_ID = "leaugeId"
        const val TEAM_ID = "teamId"
        const val COMPARE_TEAM_ID = "compareTeamId"
        const val OLD_TEAM_ID = "oldteamId"
        const val PLAYER_RESP = "playersave"
        const val CONTEST_MEMBERS = "member"
        const val CONTEST_FEES = "fee"
        const val CONTEST_CODE = "leaugeCode"
        const val CONTEST_WINNING_AMOUNT = "winningAmount"
        const val CONTEST_IS_MULTIPLE = "isMultiple"
        const val PLAYER_ID = "playerId"
        const val USER_ID = "UserId"
        const val IS_MY_LEAGUE = "myleauge"
        const val EXPIRE_TOKEN = "ExpireToken"
        const val TOPIC_NAMES = "TopicNames"
        const val AUTH_EXPIRE = "AuthExpire"
        const val LOGIN_AUTH_EXPIRE = "LoginAuthToken"
        const val ID = "ID"
        const val FRIENDS_DATA = "FriendsData"
        const val COUPON = "Coupan"
        const val PAN_NUMBER = "panNumber"
        const val PAN_NAME = "name"
        const val PAN_DOB = "dob"
        const val PAN_STATE = "stateName"
        const val PAN_PHOTO = "photo"
        const val BANK_USER_NAME = "userName"
        const val BANK_ACC_NUMBER = "accountNumber"
        const val BANK_IFSC_CODE = "ifscCode"
        const val BANK_NAME = "bankName"
        const val BANK_BRANCH = "branch"
        const val BANK_PHOTO = "photo"
        const val TRANS_TYPE_ID = "typeId"
        const val OLD_PASSWORD = "oldPassowrd"
        const val UserName = "UserName"
        const val DOB = "dob"
        const val GENDER = "gender"
        const val ADDRESS = "address"
        const val PINCODE = "PinCode"
        const val STATE = "state"
        const val AMMOUNT = "amount"
        const val AVTAR_ID = "avtarId"
        const val VERSION = "version"
        const val AMOUNT = "amount"
        const val CARD_AUTH = "Authorization"
        const val CARD_TYPE = "Type"
        const val CARD_NUMBER = "CardNumber"
        const val CARD_CVV = "Cvv"
        const val CARD_EXPIRY = "ExpiryDate"
        const val CARD_TOKEN = "cardToken"
        const val TITLE = "title"
        const val MESSAGE = "message"
        const val IP_ADDRESS = "ipAddress"
        const val MAC_ADDRESS = "macAddress"
        const val TYPE = "type"
        const val SPORT_TYPE = "sportType"
        const val SERIES_ID = "seriesId"
        const val POLL_ID = "pollId"
        const val POLL_OPTION_ID = "optionId"
        const val DEVICE_ID = "deviceId"
        const val DEVICE_ID_2 = "deviceID"
        const val LANGUAGE_CODE = "languageCode"
        const val LANGUAGE_CODE_1 = "langCode"
        const val DEVICE_TYPE = "deviceType"
        const val FAV_TEAM_ID = "teamId"
        const val TOUR_ID = "tourId"
        const val CATEGORY_NAME = "Category"
        const val WITHDRAWAL_METHOD = "WithdrawalOptionID"
        const val INNING = "InningNo"
        const val LANG_CODE = "LanguageCode"
        const val SPORTS_ID = "SportsID"
        const val CAMPAIGN_ID = "campaignId"
        const val CAMPAIGN_ID1 = "CampaignId"
        const val QUESTION_ID = "questionId"
        const val OPTION_ID = "optionId"
        const val COUPON_PASS_ID = "passId"
        const val OFFER_PASS_ID = "OfferIds"
        const val VPA = "vpa"
        const val MERCHANT_ID = "merchant_id"
        const val STATE_NAME2 = "statename"
        const val GAME_ID = "GameId"
        const val LOCATION_CORDINATE = "Coordinate"
        const val DL_NUMBER = "dLNumber"
        const val UPLOAD_DOC_TYPE = "DocType"
        const val LOCATION_COORDINATE = "Coordinate"
        const val LOCATION_COORDINATES = "Coordinate"
        const val LOCATION_STATE = "State"
        const val COORDINATES="Coordinates"


    }

}