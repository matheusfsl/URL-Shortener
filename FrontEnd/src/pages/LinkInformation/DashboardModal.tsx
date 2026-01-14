import { FaXmark } from "react-icons/fa6";
import { LuClock, LuGlobe, LuLink2, LuMousePointerClick } from "react-icons/lu";
import type { UrlServicesModel } from "../../core/services/models/UrlServicesModel";

export default function DashboardModal({
  isOpen,
  onClose,
  dashboardInfo,
}: {
  isOpen: boolean;
  dashboardInfo: UrlServicesModel.GetUrlInformations.Response;
  onClose: () => void;
}) {
  return (
    isOpen && (
      <div className="absolute size-full flex justify-center items-center bg-black/25 ">
        <div className="size-full" onClick={onClose}></div>
        <div className="absolute p-4 pt-8 w-min-fit h-min-fit rounded-lg flex flex-col bg-white shadow-black/25 shadow-md z-50">
          <button
            className="absolute end-2 top-2 btn btn-active btn-circle p-0.5 m-0 h-fit w-fit bg-transparent border-none hover:bg-base-300"
            onClick={onClose}
          >
            <FaXmark />
          </button>
          <div className="flex flex-col items-center justify-center mb-3">
            <div className="flex justify-center items-center gap-x-1.5">
              <LuLink2 className="size-7" />
              <span className="font-bold text-lg">{dashboardInfo.url}</span>
            </div>
            <span className="text-gray-400 font-medium text-sm">
              {`Criado ${new Date(
                dashboardInfo.createdAt
              ).toLocaleDateString()}`}
            </span>
          </div>
          <div className="grid grid-cols-2 gap-1">
            <div className="stats border-2 bg-white border-gray-300">
              <div className="stat">
                <div className="stat-title">Total de cliques</div>
                <div className="stat-value">{dashboardInfo.clickCount}</div>
                <div className="stat-figure">
                  <LuMousePointerClick className="scale-200" />
                </div>
              </div>
            </div>
            <div className="stats border-2 bg-white border-gray-300">
              <div className="stat">
                <div className="stat-title">Quantidade de IPs</div>
                <div className="stat-value">{dashboardInfo.uniqueIps}</div>
                <div className="stat-figure">
                  <LuGlobe className="scale-200" />
                </div>
              </div>
            </div>
            <div className="stats border-2 bg-white border-gray-300">
              <div className="stat">
                <div className="stat-title">Cliques por hora</div>
                <div className="stat-value">{`${dashboardInfo.clicksPerHour}/hora`}</div>
                <div className="stat-figure">
                  <LuClock className="scale-200" />
                </div>
              </div>
            </div>
            <div className="stats border-2 bg-white border-gray-300">
              <div className="stat">
                <div className="stat-title">
                  Tempo médio para acessar o link após a criação
                </div>
                <div className="stat-value">
                  {`${dashboardInfo.avgTimeToAccessSeconds} segundos`}
                </div>
                <div className="stat-figure">
                  <LuMousePointerClick className="scale-200" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  );
}
