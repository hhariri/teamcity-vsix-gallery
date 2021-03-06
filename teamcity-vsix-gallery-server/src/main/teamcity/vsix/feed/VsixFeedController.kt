package teamcity.vsix.feed

import com.intellij.openapi.diagnostic.Logger
import jetbrains.buildServer.controllers.BaseController
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.WebControllerManager
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class VsixFeedController(val web : WebControllerManager, val feedHandler : AtomFeedCreator) : BaseController() {
    {
        web.registerController("/app/vsix/v1/FeedService.svc/**", this)
    }

    override fun doHandle(request: HttpServletRequest, response: HttpServletResponse): ModelAndView? {
        if (!BaseController.isGet(request)) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "TeamCity provided feed is readonly.")
            return null
        }

        feedHandler.handleRequest(request, response)
        return null
    }
}

